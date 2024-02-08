// 페이징 스크립트
var page = /*[[${param.page}]]*/ null;
var pageLimit = /*[[${pu.pageLimit}]]*/ null; //화면에 page개수
if (page == null) {
    page = "1";
}
var idx = (parseInt(page) - 1) % pageLimit; //idx = index //한 화면의 페이지개수
var elements = document.querySelectorAll(".btn-page");
elements[idx].classList.add("target");

//수정버튼 활성화 로직
function toggleEditMode(button) {
    var row = button ? button.closest('.stu_data') : document.querySelector('.stu_data:focus');
    if (!row) {
        return;
    }

    var inputs = row.querySelectorAll('.stuName, .stuPhone, .stuBirth');
    var selectElementSitu = row.querySelector('.stuSitu');
    var selectElementName = row.querySelector('.proName');
    var editButton = row.querySelector('.edit_button');

    inputs.forEach(function (input) {
        input.readOnly = !input.readOnly;
    });
	
	// 편집 중이면 <select> 요소를 활성화, 아니면 비활성화
	selectElementSitu.disabled = editButton.innerText !== '수정';
	selectElementName.disabled = editButton.innerText !== '수정';
	
    if (editButton.innerText === '수정') {
		// 수정 모드로 전환 시에 프로시저 목록을 가져와서 옵션 동적 추가
        editButton.innerText = '저장';
        setEditableBackground(row);
    } else {
        editButton.innerText = '수정';
        setOriginalBackground(row);
        saveChanges(row)  // 해당 행의 정보를 전달
            .then(function (success) {
                if (success) {
                    toggleEditMode();  // 정보 수정 성공 시 수정 모드 종료
                }
            });
    }
}

document.addEventListener('DOMContentLoaded', function() {
    // 클래스가 'delete_checkbox'인 모든 체크박스에 이벤트 리스너 추가
    var checkboxes = document.querySelectorAll('.delete_checkbox');
    checkboxes.forEach(function(checkbox) {
        checkbox.addEventListener('change', function() {
            toggleRowBackground(this);
        });
    });
});

function toggleRowBackground(checkbox) {
    var row = checkbox.closest('.stu_data');
    if (!row) {
        return;
    }

    if (checkbox.checked) {
        setDelableBackground(row);
    } else {
        setDelOriginalBackground(row);
    }
}

//삭제 선택시 배경 변경
function setDelableBackground(row) {
    var inputs = row.querySelectorAll('.stuNo, .stuName, .stuPhone, .stuBirth, .proName, .stuSitu');
    inputs.forEach(function(input) {
      input.style.backgroundColor = '#BE9FE1'; // 백그라운드 색상을 하얀색으로 변경
    });
  }

  function setDelOriginalBackground(row) {
    var inputs = row.querySelectorAll('.stuNo, .stuName, .stuPhone, .stuBirth, .proName, .stuSitu');
    inputs.forEach(function(input) {
      input.style.backgroundColor = 'transparent'; // 백그라운드 색상을 원래대로 변경
    });
  }

//수정 선택시 배경 변경
function setEditableBackground(row) {
    var inputs = row.querySelectorAll('.stuName, .stuPhone, .stuBirth, .proName, .stuSitu');
    inputs.forEach(function(input) {
    	input.style.backgroundColor = '#BE9FE1'; // 백그라운드 색상을 하얀색으로 변경
    });
}

// 수정모드 해제시 배경색 초기화
function setOriginalBackground(row) {
	var inputs = row.querySelectorAll('.stuName, .stuPhone, .stuBirth, .proName, .stuSitu');
    inputs.forEach(function(input) {
    	input.style.backgroundColor = 'transparent'; // 백그라운드 색상을 원래대로 변경
    });
}
  
// 저장
function saveChanges(row) {
	console.log('saveChanges 함수 호출');
    var stuNo = row.querySelector('.stuNo').value;
    var stuName = row.querySelector('.stuName').value;
    var stuPhone = row.querySelector('.stuPhone').value;
    var stuBirth = row.querySelector('.stuBirth').value;
    var proName = row.querySelector('.proName').value;
    var stuSitu = row.querySelector('.stuSitu').value;
    
    // 유효성 검사
    if (!validateInput(stuName, stuPhone, stuBirth)) {
		alert('수정 실패. 입력정보를 다시 확인하세요.');
		window.location.reload();
        return false;
    }

    // 서버로 전송할 데이터 준비
    var data = {
        stuNo: stuNo,
        stuName: stuName,
        stuPhone: stuPhone,
        stuBirth: stuBirth,
        proName: proName,
        stuSitu: stuSitu
    };

    // 서버로 데이터를 전송
    fetch('/studentSaveChanges', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    .then(response => response.json())
    .then(data => {
        console.log('저장 완료:', data);
        if (data.success) {
            toggleEditMode();  // 정보 수정 성공 시 수정 모드 종료
        }
        return data.success;  // 저장 성공 여부 반환
    })
    .catch((error) => {
        console.error('저장 실패:', error);
        return false;  // 저장 실패 시 false 반환
    });
}

// 입력값 유효성 검사
function validateInput(stuName, stuPhone, stuBirth) {
    if (!stuName || !stuPhone || !stuBirth || stuPhone.length < 10) {
        return false;
    }
    alert('정보가 수정되었습니다.');
    return true;
}

// 숫자 이외의 문자 제거
function validateNumericInput(inputElement) {
    inputElement.value = inputElement.value.replace(/[^0-9]/g, '');
}

//삭제 로직
function deleteSelectedStudents() {
	// 확인 창 띄우기
    var result = confirm("정말로 선택하신 학생을 삭제하시겠습니까?");
    if (!result) {
        return; // 취소를 선택하면 함수 종료
    }
	
    var selectedStudents = document.querySelectorAll('.delete_checkbox:checked');
    var selectedStudentIds = [];

    selectedStudents.forEach(function (checkbox) {
        var row = checkbox.closest('.stu_data');
        var stuNo = row.querySelector('.stuNo').value;
        selectedStudentIds.push(stuNo);
    });

    if (selectedStudentIds.length === 0) {
        alert('선택된 학생이 없습니다.');
        return;
    }

    var selectedStudentsInput = document.querySelector('.selectedStudents');
    selectedStudentsInput.value = JSON.stringify(selectedStudentIds);

    // 폼을 서버로 제출
    document.getElementById('deleteForm').submit();
}

// 페이지 로딩 시 실행
$(document).ready(function() {
    function fetchProcedureNamesFromServer() {
        $.ajax({
            url: '/api/procedureNames',
            method: 'GET',
            success: function(data) {
                var proNameSelect = $('#proName');
                proNameSelect.empty(); // 기존 옵션 비우기

                data.forEach(function (proName) {
                    proNameSelect.append('<option value="' + proName + '">' + proName + '</option>');
                });
            },
            error: function(error) {
                console.error('프로시저 정보 가져오기 오류:', error);
            }
        });
    }

    // 페이지 로딩 시에 한 번 데이터를 가져오기
    fetchProcedureNamesFromServer();
});
