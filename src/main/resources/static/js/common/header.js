/**
 * 
 */
function calendar() {
  document.getElementById('list').style.left = "-255px";
  document.getElementById('listCalendar').style.left = "0";
  document.getElementById('listNotice').style.left = "-255px";
  document.getElementById('listPayDoc').style.left = "-255px";
  document.getElementById('listWork').style.left = "-255px";
  document.getElementById('listLesson').style.left = "-255px";
  document.getElementById('listDrive').style.left = "-255px";
}
function board() {
  document.getElementById('list').style.left = "-255px";
  document.getElementById('listCalendar').style.left = "-255px";
  document.getElementById('listNotice').style.left = "0";
  document.getElementById('listPayDoc').style.left = "-255px";
  document.getElementById('listWork').style.left = "-255px";
  document.getElementById('listLesson').style.left = "-255px";
  document.getElementById('listDrive').style.left = "-255px";
}
function payDoc() {
  document.getElementById('list').style.left = "-255px";
  document.getElementById('listCalendar').style.left = "-255px";
  document.getElementById('listNotice').style.left = "-255px";
  document.getElementById('listPayDoc').style.left = "0";
  document.getElementById('listWork').style.left = "-255px";
  document.getElementById('listLesson').style.left = "-255px";
  document.getElementById('listDrive').style.left = "-255px";
}
function work() {
  document.getElementById('list').style.left = "-255px";
  document.getElementById('listCalendar').style.left = "-255px";
  document.getElementById('listNotice').style.left = "-255px";
  document.getElementById('listPayDoc').style.left = "-255px";
  document.getElementById('listWork').style.left = "0";
  document.getElementById('listLesson').style.left = "-255px";
  document.getElementById('listDrive').style.left = "-255px";
}
function lesson() {
  document.getElementById('list').style.left = "-255px";
  document.getElementById('listCalendar').style.left = "-255px";
  document.getElementById('listNotice').style.left = "-255px";
  document.getElementById('listPayDoc').style.left = "-255px";
  document.getElementById('listWork').style.left = "-255px";
  document.getElementById('listLesson').style.left = "0";
  document.getElementById('listDrive').style.left = "-255px";
}
function drive() {
  document.getElementById('list').style.left = "-255px";
  document.getElementById('listCalendar').style.left = "-255px";
  document.getElementById('listNotice').style.left = "-255px";
  document.getElementById('listPayDoc').style.left = "-255px";
  document.getElementById('listWork').style.left = "-255px";
  document.getElementById('listLesson').style.left = "-255px";
  document.getElementById('listDrive').style.left = "0";
}
function openAddDoc() {
  document.getElementById('addDocList').classList.toggle('active');
}









var stompClient=null; 

$(function(){
	$("#btn-bot").click(btnBotClicked);
});
function btnCloseClicked(){
	$("#bot-container").hide();
	//대화창 리셋
	$("#chat-content").html("");
	disconnect();
	
}
function btnBotClicked(){
	//1. 소켓 접속
	$("#bot-container").show();
	connect()
}

//1~9 -> 01~09 변환하는 함수
function fomatNumber(number){
	return number<10? '0'+number:''+number;
}

// 시간출력 ex) 오전 9:08
function formatTime(){
	var now=new Date();
	var ampm=(now.getHours()>11)?"오후":"오전";
	var hour=now.getHours()%12;
	if(hour==0)hour=12;
	return `${ampm} ${hour}:${fomatNumber(now.getMinutes())}`;
}

function userTag(text){
	var time=formatTime();
	return `
	<div class="msg user flex">
		<div class="message">
			<div class="part">
				<p>${text}</p>
			</div>
			<div class="time">${time}</div>
		</div>
	</div>
	`;
}

function showMessage(tag){
	$("#chat-content").append(tag);
	//스크롤을 제일 아래로
	$("#chat-content").scrollTop($("#chat-content").prop("scrollHeight"));
}
let key;
function connect(){
	//var socket=new SockJS("/green-bot")
	stompClient=Stomp.over(new SockJS("/green-bot"));
	stompClient.connect({},(frame)=>{
		key=new Date().getTime();
		//접속이 완료되면 인사말수신-구독
		stompClient.subscribe(`/topic/question/${key}`,(answerData)=>{
			//console.log(answerData.body);
			var msg=answerData.body;
			//var text=message.content;
			/////////////////////////////
//			var tag=botTag(message);
			/////////////////////////////
			showMessage(msg);
		})
		
		var data={
			key: key,
			name:"그린",
			content: "인사말"
		}
		//인사말 보내줘
		stompClient.send("/message/bot",{},JSON.stringify(data));
	});
}
function disconnect() {
    stompClient.disconnect(function(){
		console.log("Disconnected");	
	});
    
}
function checkEnterKey(event){
	var keyCode=event.keyCode;
	if(keyCode===13){
		btnMsgSendClicked();
	}
}

function btnMsgSendClicked(){
	var content=$("#question").val().trim();
	
	if(content.length <2 ){
		alert("질문은 2글자 이상이어야 합니다.");
		return;
	}
	
	var data={
			key: key,
			name:"그린",
			content: content
		}
	//인사말 보내줘
	stompClient.send("/message/bot",{},JSON.stringify(data));
	var tag=userTag(content);
	showMessage(tag);
	$("#question").val("");
}