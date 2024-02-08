//셀렉트에서 옵션 선택시 포커스 해제
function blurSelect(selectElement) {
    selectElement.blur();
}

//생년월일 셀렉트
document.addEventListener('DOMContentLoaded', function () {
    // 출생 연도 옵션 추가
    const birthYearSelect = document.getElementById('birth-year');
    const currentYear = new Date().getFullYear();
    for (let year = 1980; year <= currentYear; year++) {
        const option = document.createElement('option');
        option.value = year;
        option.textContent = year + '년';
        birthYearSelect.appendChild(option);
    }

    // 월 옵션 추가
    const birthMonthSelect = document.getElementById('birth-month');
    for (let month = 1; month <= 12; month++) {
        const option = document.createElement('option');
        option.value = month;
        option.textContent = month + '월';
        birthMonthSelect.appendChild(option);
    }

    // 일 옵션 추가
    const birthDaySelect = document.getElementById('birth-day');
    for (let day = 1; day <= 31; day++) {
        const option = document.createElement('option');
        option.value = day;
        option.textContent = day + '일';
        birthDaySelect.appendChild(option);
    }
});

// 숫자 이외의 문자 제거
function validateNumericInput(inputElement) {
    inputElement.value = inputElement.value.replace(/[^0-9]/g, '');
}

// 과정 등록 페이지 접근시 수강 과목 옵션 추가
$(document).ready(function() {
	// 서버로부터 데이터를 가져와서 옵션에 추가하는 함수
	function fetchProNamesFromServer() {
		$.ajax({
			url: '/api/procedureNames',  // 변경된 엔드포인트
			method: 'GET',
			success: function(data) {
				var proNameSelect = $('#proNameSelect');
				proNameSelect.empty();  // 기존 옵션을 비워줍니다.

				// 기본 옵션 추가
				proNameSelect.append('<option value="" selected disabled>수강 과목을 선택해주세요</option>');

				// 서버에서 받아온 데이터를 순회하면서 옵션을 추가
				data.forEach(function(proName) {
					proNameSelect.append('<option value="' + proName + '">' + proName + '</option>');
				});
			},
			error: function(error) {
				console.error('Error fetching pro names:', error);
			}
		});
	}

	// 페이지 로딩 시에 한 번 데이터를 가져오기
	fetchProNamesFromServer();
});

function combineBirthDay() {
    var year = document.getElementById("birth-year").value;
    var month = document.getElementById("birth-month").value;
    var day = document.getElementById("birth-day").value;

    // 추가: 모든 셀렉트 박스에서 선택된 값이 없으면 false 반환
    if (year === "" || month === "" || day === "") {
        // 추가: 경고 메시지 표시
        return false;
    }

    // 추가: 유효한 날짜인지 확인
    if (!isValidDate(year, month, day)) {
        return false;
    }

    // 원래의 로직
    var fullBirthDay = year + "-" + (month.length === 1 ? "0" + month : month) + "-" + (day.length === 1 ? "0" + day : day);
    document.getElementById("fullBirthDay").value = fullBirthDay;

    // 확인을 위해 콘솔에 출력
    console.log("Full Birth Day:", fullBirthDay);

    return true;
}

// 추가: 생년월일이 유효한지 확인하는 함수
function isValidDate(year, month, day) {
    var date = new Date(year, month - 1, day); // month는 0부터 시작하므로 -1 해줌
    return (
        date.getFullYear() == year &&
        date.getMonth() + 1 == month &&
        date.getDate() == day
    );
}



//유효성검사
// 수강생 유효성 검사를 위한 스크립트
function validateStuName() {
    var stuName = document.forms["stuAddForm"]["stuName"].value;
	
	// 정규 표현식을 사용하여 한글 또는 영어만 허용
    var regex = /^[a-zA-Z가-힣]+$/;
	
    if (stuName === "" || !regex.test(stuName)) {
        return false;
    } else {
        return true;
    }
}

// 연락처 유효성 검사를 위한 스크립트
function validateStuPhone() {
    var stuPhone = document.forms["stuAddForm"]["stuPhone"].value;

    if (stuPhone.length < 10) {
        return false;
    } else {
        return true;
    }
}

// 생년월일 유효성 검사를 위한 스크립트
function validateStuBirth() {
    var stuBirth = document.forms["stuAddForm"]["stuBirth"].value;

    if (stuBirth === "") {
        return false;
    } else {
        return true;
    }
}

// 수강과목 유효성 검사를 위한 스크립트
function validateProName() {
    var proName = document.forms["stuAddForm"]["proName"].value;

    if (proName === "") {
        return false;
    } else {
        return true;
    }
}

// 모든 유효성 검사와 알림 메시지를 확인하는 스크립트
function validateSubmit() {
    // 각 유효성 검사 함수를 호출하여 결과를 얻음
    var isValidStuName = validateStuName();
	var isValidStuPhone = validateStuPhone();
	var isValidStuBirth = validateStuBirth();
	var isValidProName = validateProName();
	var isCombineBirthDay = combineBirthDay();
	
    // 모든 유효성 검사를 확인하고 결과에 따라 처리
    if (isValidStuName && isValidStuPhone && isValidStuBirth && isValidProName && isCombineBirthDay) {
        // 모든 유효성 검사가 통과하면 성공 메시지를 표시
        alert("학생이 등록되었습니다.");
        return true; // 폼이 서버로 제출됨
    } else {
        // 하나 이상의 유효성 검사가 실패하면 오류 메시지를 표시
        alert("입력 정보를 다시 확인하세요.");
        return false; // 폼 제출을 취소함
    }
}