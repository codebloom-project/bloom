//페이지 로드 시 포커스 지정
window.onload = function() {
	//포커스 영역 id 저장
	const onloadTarget = document.getElementById("onloadTarget");
	
	//로그인, 회원 가입, 계정 찾기 form id 저장
	const signUp = document.getElementById("sign-up");
	const findAccount = document.getElementById("find-account");
	
	onloadTarget.focus();
	signUp.classList.add("animateSwitch");
	findAccount.classList.add("animateSwitch");
}

//버튼 클릭 시 form 전환
function signSwitch() {
	//로그인, 회원 가입, 계정 찾기 form id 저장
	const signIn = document.getElementById("sign-in");
	const signUp = document.getElementById("sign-up");
	const findAccount = document.getElementById("find-account");
	
	//로그인, 회원 가입, 계정 찾기 button id 저장
	const signInBtnToSignUp = document.getElementById("sign-in-btn-1");
	const signInBtnToFindAccount = document.getElementById("sign-in-btn-2");
	const signUpBtn = document.getElementById("sign-up-btn");
	const findAccountBtn = document.getElementById("find-account-btn");
	
	//로그인에서 회원가입으로 form 전환
	signUpBtn.addEventListener("click", () => {
		signIn.classList.remove("show");
		signUp.classList.remove("hide");
		
		signIn.classList.add("hide");
		signUp.classList.add("show");
	});
	
	//로그인에서 계정 찾기로 form 전환
	findAccountBtn.addEventListener("click", () => {
		signIn.classList.remove("show");
		findAccount.classList.remove("hide");
		
		signIn.classList.add("hide");
		signIn.classList.add("animateSwitch");
		findAccount.classList.add("show");
	});
	
	//회원 가입에서 로그인으로 form 전환
	signInBtnToSignUp.addEventListener("click", () => {
		signUp.classList.remove("show");
		signIn.classList.remove("hide");
		
		signUp.classList.add("hide");
		signIn.classList.add("show");
		signIn.classList.add("animateSwitch");
	});
	
	//계정 찾기에서 로그인으로 form 전환
	signInBtnToFindAccount.addEventListener("click", () => {
		findAccount.classList.remove("show");
		signIn.classList.remove("hide");
		
		findAccount.classList.add("hide");
		signIn.classList.add("show");
		signIn.classList.add("animateSwitch");
	});
}

//라디오 버튼 클릭 시 부모 요소 색상 변경
function changeColor(element) {
	var signInputDept = document.getElementById("sign-input-dept");
	var parentElement = element.parentNode;
	var previousColorElement = document.querySelector('.sign-input-dept .change-color');
	
	signInputDept.classList.add("validation-passed");
	parentElement.classList.add("change-color");
	
  if (previousColorElement) {
		previousColorElement.classList.remove("change-color");
	}
}

document.addEventListener("DOMContentLoaded", signSwitch);