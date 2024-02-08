//사용자 계정 유효성 검사
async function validateEmpUsername() {
	//사용자 계정의 입력값
	var empUsername = document.forms["sign-up-form"]["empUsername"].value;
	
	//사용자 계정 정규 표현식 
	const empUsernameRegex = /^[a-z0-9\-_]{5,20}$/;
	
	//메시지 영역 id
	const inputEmpUsername = document.getElementById("emp-username");
	const errorMessage = document.getElementById("validation-message-1");
	const empUsernameError1 = document.getElementById("username-error-1");
	const empUsernameError2 = document.getElementById("username-error-2");
	
	//정규 표현식 에러 메시지
	if (!empUsernameRegex.test(empUsername)) {
		empUsernameError1.innerText = "사용할 수 없는 계정입니다."
		empUsernameError2.innerText = "5 ~ 20자의 영문 소문자, 숫자와 특수문자 '-', '_'만 사용할 수 있습니다."
		
		inputEmpUsername.classList.remove("validation-passed")
		errorMessage.classList.remove("hide");
		
		inputEmpUsername.classList.add("validation-failed");
		errorMessage.classList.add("show");
		
		return false;
	}
	
	//비동기 중복 검사 처리, 메시지
	try {
		var isExists = await existsByEmpUsername(empUsername + "@codebloom.com");
		
		if (isExists) {
			empUsernameError1.innerText = "사용할 수 없는 계정입니다."
			
			inputEmpUsername.classList.remove("validation-passed")
			errorMessage.classList.remove("hide");
			
			inputEmpUsername.classList.add("validation-failed");
			errorMessage.classList.add("show");
			empUsernameError2.classList.add("hide");
			
			return false;
		} else {
			inputEmpUsername.classList.remove("validation-failed")
			errorMessage.classList.remove("show");
			
			inputEmpUsername.classList.add("validation-passed")
			errorMessage.classList.add("hide");
			
			return true;
		}
	} catch (error) {
		console.error(error);
	}
}

//비동기 사용자 계정 중복 검사
function existsByEmpUsername(empUsername) {
	return new Promise(function(resolve, reject) {
		$.ajax({
			url: "/sign/exists-username",
			type: "POST",
			data: {empUsername: empUsername},
			success: function(isExists) {
				console.log(isExists);
				resolve(isExists);
			},
			error: function(xhr, status, error) {
				reject(error);
			}
		});
	});
}

//비밀번호 유효성 검사
function validateEmpPassword() {
	//비밀번호 입력값
	var empPassword = document.forms["sign-up-form"]["empPassword"].value;
	
	//비밀번호 정규 표현식
	const empPasswordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
	
	//메시지 영역 id
	const inputEmpPassword = document.getElementById("emp-password");
	const errorMessage = document.getElementById("validation-message-2");
	const empPasswordError1 = document.getElementById("password-error-1"); 
	const empPasswordError2 = document.getElementById("password-error-2"); 
	
	//정규 표현식 에러 메시지
	if (!empPasswordRegex.test(empPassword)) {
		empPasswordError1.innerText = "사용할 수 없는 비밀번호입니다."
		empPasswordError2.innerText = "8자 이상의 영문 대소문자, 숫자와 특수문자를 모두 포함하여 사용해주세요."
		
		inputEmpPassword.classList.remove("validation-passed");
		errorMessage.classList.remove("hide");
		
		inputEmpPassword.classList.add("validation-failed");
		errorMessage.classList.add("show");
		
		return false;
	} else {
		inputEmpPassword.classList.remove("validation-failed")
		errorMessage.classList.remove("show");
		
		inputEmpPassword.classList.add("validation-passed")
		errorMessage.classList.add("hide");
		
		return true;
	}
}

//비밀번호 확인 유효성 검사
function validateConfirmEmpPassword() {
	//비밀번호, 비밀번호 확인 입력값
	var confirmEmpPassword = document.forms["sign-up-form"]["empPassword"].value;
	var empPassword = document.forms["sign-up-form"]["confirmEmpPassword"].value;
	
	//비밀번호 정규 표현식
	const empPasswordRegex = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
	
	//메시지 영역 id
	const inputConfirmEmpPassword = document.getElementById("confirm-emp-password");
	const errorMessage = document.getElementById("validation-message-3");
	const ConfirmEmpPasswordError = document.getElementById("confirm-password-error");
	
	//정규 표현식, 비밀번호 확인 에러 메시지
	if (!empPasswordRegex.test(empPassword) || confirmEmpPassword !== empPassword) {
		ConfirmEmpPasswordError.innerText = "사용할 수 없는 비밀번호입니다."
		
		inputConfirmEmpPassword.classList.remove("validation-passed");
		errorMessage.classList.remove("hide");
		
		inputConfirmEmpPassword.classList.add("validation-failed");
		errorMessage.classList.add("show");
		
		return false;
	} else {
		inputConfirmEmpPassword.classList.remove("validation-failed")
		errorMessage.classList.remove("show");
		
		inputConfirmEmpPassword.classList.add("validation-passed")
		errorMessage.classList.add("hide");
		
		return true;
	}
}

//이름 유효성 검사
function validateEmpName() {
  var empName = document.forms["sign-up-form"]["empName"].value;
	
	//메시지 영역 id
	const inputEmpName = document.getElementById("emp-name");
	const errorMessage = document.getElementById("validation-message-4");
	const EmpNameError = document.getElementById("name-error");
	
  if (empName == "") {
    EmpNameError.innerText = "이름을 입력해주세요.";
    
    inputEmpName.classList.remove("validation-passed");
		errorMessage.classList.remove("hide");
    
    inputEmpName.classList.add("validation-failed");
		errorMessage.classList.add("show");
		
    return false;
  } else {
		inputEmpName.classList.remove("validation-failed")
		errorMessage.classList.remove("show");
		
    inputEmpName.classList.add("validation-passed")
		errorMessage.classList.add("hide");
		
    return true;
  }
}