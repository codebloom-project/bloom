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
    var row = button ? button.closest('.pro_data') : document.querySelector('.pro_data:focus');
    if (!row) {
        return;
    }

    var inputs = row.querySelectorAll('.proName, .proType, .proStart, .proEnd');
    var selectElementClass = row.querySelector('.proClass');
    var selectElementType = row.querySelector('.proType');
    var selectElementName = row.querySelector('.empName');
    var editButton = row.querySelector('.edit_button');

    // 편집 가능 여부를 토글
    inputs.forEach(function (input) {
        input.readOnly = !input.readOnly;
    });

    // 편집 중이면 <select> 요소를 활성화, 아니면 비활성화
    selectElementClass.disabled = editButton.innerText !== '수정';
    selectElementType.disabled = editButton.innerText !== '수정';
    selectElementName.disabled = editButton.innerText !== '수정';

    if (editButton.innerText === '수정') {
        editButton.innerText = '저장';
        setEditableBackground(row);
    } else {
        editButton.innerText = '수정';
        setOriginalBackground(row);
        saveChanges(row);  // 해당 행의 정보를 전달
    }
}

// 수정모드 배경색 변환 로직
function setEditableBackground(row) {
    var inputs = row.querySelectorAll('.proName, .proClass, .proType, .empName, .proStart, .proEnd');
    inputs.forEach(function (input) {
        input.style.backgroundColor = '#BE9FE1';
    });
}

// 수정모드 해제시 배경색 초기화
function setOriginalBackground(row) {
    var inputs = row.querySelectorAll('.proName, .proClass, .proType, .empName, .proStart, .proEnd');
    inputs.forEach(function (input) {
        input.style.backgroundColor = 'transparent';
    });
}

// 저장
function saveChanges(row) {
    console.log('saveChanges 함수 호출');
    var proNo = row.querySelector('.proNo').value;
    var proName = row.querySelector('.proName').value;
    var proClass = row.querySelector('.proClass').value;
    var proType = row.querySelector('.proType').value;
    var empName = row.querySelector('.empName').value;
    var proStart = row.querySelector('.proStart').value;
    var proEnd = row.querySelector('.proEnd').value;

    // 유효성 검사
    if (!validateInput(proName, proStart, proEnd)) {
        alert('수정 실패. 입력정보를 다시 확인하세요.');
        window.location.reload();
        return false;
    }

    // 서버로 전송할 데이터 준비
    var data = {
        proNo: proNo,
        proName: proName,
        proClass: proClass,
        proType: proType,
        empName: empName,
        proStart: proStart,
        proEnd: proEnd
    };

    console.log('전송 데이터:', data);

    // 서버로 데이터를 전송
    fetch('/saveChanges', {
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
            toggleEditMode();  // 수정 모드 종료
        }
        return data.success;
    })
    .catch((error) => {
        console.error('저장 실패:', error);
        return false;
    });
}

// 입력값 유효성 검사
function validateInput(proName, proStart, proEnd) {
    if (!proName || !proStart || !proEnd) {
        return false;
    }
    alert('정보가 수정되었습니다.');
    return true;
}

// 페이지 로딩 시 실행
$(document).ready(function() {
    function fetchEployeeNamesFromServer() {
        $.ajax({
            url: '/api/employeeNames',
            method: 'GET',
            success: function(data) {
                var empNameSelect = $('#empName');
                empNameSelect.empty(); // 기존 옵션 비우기

                data.forEach(function (proName) {
                    empNameSelect.append('<option value="' + empName + '">' + empName + '</option>');
                });
            },
            error: function(error) {
                console.error('프로시저 정보 가져오기 오류:', error);
            }
        });
    }

    // 페이지 로딩 시에 한 번 데이터를 가져오기
    fetchEployeeNamesFromServer();
});
