// 과정 등록 페이지 접근시 담당 강사 옵션 추가
$(document).ready(function() {
	// 서버로부터 데이터를 가져와서 옵션에 추가하는 함수
	function fetchEmpNamesFromServer() {
		$.ajax({
			url: '/api/employeeNames',  // 변경된 엔드포인트
			method: 'GET',
			success: function(data) {
				var empNameSelect = $('#empNameSelect');
				empNameSelect.empty();  // 기존 옵션을 비워줍니다.

				// 기본 옵션 추가
				empNameSelect.append('<option value="" selected disabled>담당 강사를 선택해주세요</option>');

				// 서버에서 받아온 데이터를 순회하면서 옵션을 추가
				data.forEach(function(empName) {
					empNameSelect.append('<option value="' + empName + '">' + empName + '</option>');
				});
			},
			error: function(error) {
				console.error('Error fetching emp names:', error);
			}
		});
	}

	// 페이지 로딩 시에 한 번 데이터를 가져오기
	fetchEmpNamesFromServer();
});

//시작일 종료일 캘린더
$(function() {
	$(".datepicker").css("display", "none");

	//시작일 캘린더
	$("#datepicker").datepicker({
		dateFormat: 'yy-mm-dd',
		prevText: '이전 달',
		nextText: '다음 달',
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		showMonthAfterYear: true,
		yearSuffix: '년',
		minDate: 1,
		onSelect: function(date) {
			// 시작일이 선택되면 종료일 캘린더의 최소 날짜를 설정
			var selectedDate = new Date(date);
			selectedDate.setDate(selectedDate.getDate() + 1);

			$("#selectedStartDate").val(date);
			$("#selectedEndDate").val(""); // 종료일 초기화
			$("#datepicker2").datepicker('option', 'minDate', selectedDate);
			$(".datepicker").hide();

			// 시작일이 선택되었다면 종료일 캘린더 버튼 활성화
			$("#endDateBtn").prop("disabled", false);
		}
	});

	//종료일 캘린더
	$("#datepicker2").datepicker({
		dateFormat: 'yy-mm-dd',
		prevText: '이전 달',
		nextText: '다음 달',
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		showMonthAfterYear: true,
		yearSuffix: '년',
		beforeShow: function() {
			// 종료일 캘린더를 열기 전에 시작일이 선택되었는지 확인
			var startDate = $("#selectedStartDate").val();
			if (!startDate) {
				// 시작일이 선택되지 않았다면 종료일 캘린더 버튼 비활성화
				$("#endDateBtn").prop("disabled", true);
				return false;
			}


		},
		onSelect: function(date) {
			$("#selectedEndDate").val(date);
			$(".datepicker").hide();
		}
	});

	// 문서 클릭 이벤트
	$(document).on("click", function(event) {
		var calendarContainer = $(".datepicker");

		// 클릭된 요소가 캘린더 영역 내에 있는지 확인
		if (!calendarContainer.is(event.target) && calendarContainer.has(event.target).length === 0) {
			// 클릭된 요소가 캘린더 영역 외부에 있을 경우 캘린더를 닫음
			if ($(event.target).closest('.ui-datepicker-header').length === 0) {
				// 캘린더의 헤더 부분이 아닌 경우에만 닫도록 설정
				calendarContainer.hide();
			}
		}
	});

	// 시작일 캘린더 동작
	$("#startDateBtn").click(function(event) {
		event.stopPropagation(); // 이벤트 전파를 막음
		$("#datepicker").toggle();
		$("#datepicker2").hide();
	});

	// 종료일 캘린더 동작
	$("#endDateBtn").click(function(event) {
		event.stopPropagation(); // 이벤트 전파를 막음
		$("#datepicker2").toggle();
		$("#datepicker").hide();
	});

	// 시작일이 없을 때 종료일 버튼 비활성화
	$("#endDateBtn").prop("disabled", true);
});

//셀렉트에서 옵션 선택시 포커스 해제
function blurSelect(selectElement) {
    selectElement.blur();
}

// 과정명 유효성 검사를 위한 스크립트
function validateProName() {
    var proName = document.forms["proAddForm"]["proName"].value;

    if (proName === "") {
        return false;
    } else {
        return true;
    }
}

// 담당 강사 유효성 검사를 위한 스크립트
function validateEmpName() {
    var empName = document.forms["proAddForm"]["empName"].value;

    if (empName === "") {
        return false;
    } else {
        return true;
    }
}

// 반 유효성 검사를 위한 스크립트
function validateProClass() {
    var proClass = document.forms["proAddForm"]["proClass"].value;

    if (proClass === "") {
        return false;
    } else {
        return true;
    }
}

// 악기 유효성 검사를 위한 스크립트
function validateProType() {
    var proType = document.forms["proAddForm"]["proType"].value;

    if (proType === "") {
        return false;
    } else {
        return true;
    }
}

// 개강일 유효성 검사를 위한 스크립트
function validateProStart() {
    var proStart = document.forms["proAddForm"]["proStart"].value;

    if (proStart === "") {
        return false;
    } else {
        return true;
    }
}

// 종강일 유효성 검사를 위한 스크립트
function validateProEnd() {
    var proEnd = document.forms["proAddForm"]["proEnd"].value;

    if (proEnd === "") {
        return false;
    } else {
        return true;
    }
}

// 모든 유효성 검사와 알림 메시지를 확인하는 스크립트
function validateAndSubmit() {
    // 각 유효성 검사 함수를 호출하여 결과를 얻음
    var isValidProName = validateProName();
    var isValidEmpName = validateEmpName();
    var isValidProClass = validateProClass();
    var isValidProType = validateProType();
    var isValidProStart = validateProStart();
    var isValidProEnd = validateProEnd();

    // 모든 유효성 검사를 확인하고 결과에 따라 처리
    if (isValidProName && isValidEmpName && isValidProClass && isValidProType && isValidProStart && isValidProEnd) {
        // 모든 유효성 검사가 통과하면 성공 메시지를 표시
        alert("과정이 등록되었습니다.");
        return true; // 폼이 서버로 제출됨
    } else {
        // 하나 이상의 유효성 검사가 실패하면 오류 메시지를 표시
        alert("입력 정보를 다시 확인하세요.");
        return false; // 폼 제출을 취소함
    }
}
