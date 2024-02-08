/**
 * 
 */

function procedure1() {
	var procedure = document.getElementById("procedure");
	var procedureNone = document.getElementById("procedureNone");

	// 체크박스 상태에 따라 가시성을 토글합니다.
	procedureNone.style.display = procedure.checked ? "table-row" : "none";
}

function log() {
	var event = document.getElementById("event");
	var procedure = document.getElementById("procedure");
	var procedureNone = document.querySelectorAll("#procedureNone input[type='checkbox']");
	var dateInputs = document.querySelectorAll("input[type='date']");

	console.log("일정", event.checked);
	console.log("과정", procedure.checked);

	procedureNone.forEach(function(checkbox) {
		console.log("과정상세", checkbox.value, checkbox.checked);
	});
	dateInputs.forEach(function(dateInput) {
		console.log("날짜", dateInput.value);
	});

}