function detailsSwitch() {
	const detailsEnableBtn = document.querySelectorAll(".employee-list");
	
	detailsEnableBtn.forEach((btn) => {
		var empNo = btn.querySelector(".employee-no").value;
		
		btn.addEventListener("click", () => {
			
			
			employeeDetails(empNo, () => {
				//상세 정보, 수정 container id 수집
				const detailsWrap = document.getElementById("employee-details-wrap");
				const modifyWrap = document.getElementById("employee-modify-wrap");
				
				//상세 정보, 수정 button id 수집
				const detailsDisableBtn = document.getElementById("employee-details-disable");
				const modifyEnableBtn = document.getElementById("employee-details-modify");
				const modifyDisableBtn = document.getElementById("employee-modify-disable");
				
				//상세 정보 활성화
				detailsWrap.classList.remove("hide");
				detailsWrap.classList.add("show");
				detailsWrap.classList.add("animateSwitch");
				
				//상세 정보 비활성화
				detailsDisableBtn.addEventListener("click", () => {
					detailsWrap.classList.remove("show");
					detailsWrap.classList.add("hide");
				});
				
				//상세 정보 수정 활성화
				modifyEnableBtn.addEventListener("click", () => {
					detailsWrap.classList.remove("show");
					detailsWrap.classList.add("hide");
			
					modifyWrap.classList.remove("hide");
					modifyWrap.classList.add("show");
					modifyWrap.classList.add("animateSwitch");
				});
				
				//상세 정보 수정 비활성화
				modifyDisableBtn.addEventListener("click", () => {
					modifyWrap.classList.remove("show");
					modifyWrap.classList.add("hide");
				});
			});
		});
	});
}

//사원 상세 정보 비동기 처리
function employeeDetails(empNo, callback) {
	fetch("/employee/details/" + empNo, {
		method: 'GET'
	})
		.then(response => {
			if (!response.ok) {
				throw new Error('Error');
			}
			return response.text();
		})
		.then(data => {
			// ModelAndView 객체에 저장된 데이터를 View에 불러오기
			document.getElementById('employee-details-container').innerHTML = data;

			// 데이터를 모두 불러온 후 employeeDetails() 함수 실행
			if (typeof callback === 'function') {
				callback();
			}
		})
		.catch(error => {
			console.error('Fetch Error:', error);
		});
}

//HTML 문서 전체 읽은 후 함수 호출
document.addEventListener("DOMContentLoaded", detailsSwitch);