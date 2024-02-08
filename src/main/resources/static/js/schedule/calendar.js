/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
	//ajax로 서버에서 데이터 요청하여 db에 있는 값을 읽어옴
	/////////////////////////////////////////////////////
	//////////////////////// 조회 ////////////////////////
	/////////////////////////////////////////////////////
	$(function() {
		var request = $.ajax({
			url: "/calendar",
			method: "GET",
			dataType: "json"
		});
		//////////////////요청이 완료되면 실행
		request.done(function(data) {

			var calendarEl = document.getElementById('calendar');
			var calendar = new FullCalendar.Calendar(calendarEl, {
				//풀캘린더 부분 설정
				fixedWeekCount: false,
				initialView: 'dayGridMonth',
				slotMinTime: '08:00',
				slotMaxTime: '20:00',
				editable: true,
				selectable: true,
				dayMaxEvents: true,
				displayEventTime: true,
				locale: 'ko',

				//풀캘린더 헤더 부분쪽 설정
				headerToolbar: {
					left: 'prev,next,today',
					center: 'title',
					right: 'dayGridMonth,timeGridWeek,timeGridDay'
				},
				buttonText: {
					today: '오늘',
					month: '월간',
					week: '주간',
					day: '일간',
					list: '리스트'
				},
				//날짜 정보 수정
				titleFormat: function(date) {
					year = date.date.year;
					month = date.date.month + 1;
					return year + "년 " + month + "월";
				},
				dayCellContent: function(arg) {
					var date = arg.date;
					var dayOfMonth = date.getDate();
					return dayOfMonth;
				},
				/////////////////리스트에 db에서 가져온 값을 뿌려줌
				events: data.map(function(event) {
					console.log('이벤트 no:', event.no);
					console.log('내용 : ', event.content);
					var endDate = new Date(event.end);
					endDate.setHours(23, 59, 59, 999);
					return {
						id: event.no,
						title: event.title,
						start: event.start,
						end: endDate,
						categori: event.categori,
						content: event.content,
					}
					// 날짜가 낮은 순으로 배치
				}).sort(function(a, b) {
					return a.start - b.start;
				}),

				//boolean값에 따른 색 변경  [일정 및 휴가]
				eventClassNames: function(arg) {
					var eventColorBackground = arg.event.extendedProps.categori ? 'false-class' : 'true-class';
					return [eventColorBackground];
				},


			});
			calendar.render();

			///////////////////클릭한 제목으로 db에서 값을 찾아 불러와서 모달에 띄워줌

			var eventListItems = document.querySelectorAll('.eventList');
			eventListItems.forEach(function(item) {
				item.addEventListener('click', function(e) {
					if (e.target.classList.contains('123')) {
						var title = e.target.textContent;
						var eventContent = '';

						//제목으로 값 찾아오기
						var clickEvent = data.find(function(event) {
							return event.title == title;
						});

						if (clickEvent) {
							eventContent = clickEvent.content;
						}

						//리스트와 모달창의 시간이 맞지 않아 시간 조정을 하였음
						var plus9StartDate = new Date(new Date(clickEvent.start).getTime() + 9 * 60 * 60 * 1000);
						var plus9EndDate = new Date(new Date(clickEvent.end).getTime() + 9 * 60 * 60 * 1000);

						var eventStartDay = plus9StartDate.toISOString().slice(0, 16);
						var eventEndDay = plus9EndDate.toISOString().slice(0, 16);
						console.log(eventStartDay);

						document.getElementById('modalTitleInput').value = title;
						document.getElementById('modalStartDayInput').value = eventStartDay;
						document.getElementById('modalEndDayInput').value = eventEndDay;
						document.getElementById('modalContentInput').value = eventContent;


						modal.style.display = 'block';

						var editButton = document.getElementById('editButton');
						var deleteButton = document.getElementById('deleteButton');

						//////////////수정 버튼을 눌렀을때의 이벤트 설정
						editButton.addEventListener('click', function() {
							if (editButton.textContent === '수정') {
								editButton.textContent = '완료';
								deleteButton.textContent = '취소';

								document.getElementById('modalTitleInput').readOnly = false;
								document.getElementById('modalStartDayInput').readOnly = false;
								document.getElementById('modalEndDayInput').readOnly = false;
								document.getElementById('modalContentInput').readOnly = false;
							} else {
								// 위에서 가져온 데이터를 가져옴
								var editedData = {
									id: clickEvent.no,
									title: document.getElementById('modalTitleInput').value,
									content: document.getElementById('modalContentInput').value,
									start: document.getElementById('modalStartDayInput').value,
									end: document.getElementById('modalEndDayInput').value
								};
								/////////////////////////////////////////////////////
								//////////////////////// 수정 ////////////////////////
								/////////////////////////////////////////////////////
								$.ajax({
									url: "/schedule-manager/" + editedData.id,
									method: "PUT",
									contentType: "application/json",
									data: JSON.stringify(editedData),
									success: function() {
										alert("수정이 성공적으로 되었습니다.");
										modal.style.display = 'none';

										// 수정이 완료되면 수정 버튼을 다시 '수정'으로 변경
										editButton.textContent = '수정';

										// 수정 완료 후 입력 상자를 읽기 전용으로 변경
										document.getElementById('modalTitleInput').readOnly = true;
										document.getElementById('modalStartDayInput').readOnly = true;
										document.getElementById('modalEndDayInput').readOnly = true;
										document.getElementById('modalContentInput').readOnly = true;
									},
									error: function() {
										alert("수정 중에 오류가 발생하였습니다.");
									}
								});
							}
						});
						// 삭제 버튼을 눌렀을때의 이벤트
						deleteButton.addEventListener('click', function() {
							var deleteData = {
								id: clickEvent.no
							};

							if (deleteButton.textContent === '취소') {
								deleteButton.textContent = '삭제';
								editButton.textContent = '수정';

								document.getElementById('modalTitleInput').readOnly = true;
								document.getElementById('modalStartDayInput').readOnly = true;
								document.getElementById('modalEndDayInput').readOnly = true;
								document.getElementById('modalContentInput').readOnly = true;
							} else {
								var confirmDelete = confirm('정말로 일정을 삭제하시겠습니까?');
								/////////////////////////////////////////////////////
								//////////////////////// 삭제 ////////////////////////
								/////////////////////////////////////////////////////
								if (confirmDelete) {
									$.ajax({
										url: "/schedule-manager/" + deleteData.id,
										method: "DELETE",
										success: function(response) {
											alert("삭제가 성공적으로 되었습니다.");
											modal.style.display = 'none';
										},
									});
								}
							}
						});

					}
				});
			});

			//			showEventList(data);
		});

		//forEach를 돌려 이벤트 list 형태를 만듬

		//		function showEventList(data) {
		//			var eventList = document.getElementById('eventList');
		//			eventList.innerHTML = ''; // Clear existing list
		//
		//			data.forEach(function(eventData) {
		//				var listItem = document.createElement('li');
		//				var titleLine = document.createElement('button');
		//				var dateLine = document.createElement('div');
		//
		//				titleLine.textContent = eventData.title;
		//
		//				var startTime = new Date(eventData.start).toLocaleTimeString([], { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' });
		//				var endTime = new Date(eventData.end).toLocaleTimeString([], { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' });
		//
		//				dateLine.innerHTML = startTime + '<p>~<p>' + endTime;
		//
		//				listItem.appendChild(titleLine);
		//				listItem.appendChild(dateLine);
		//
		//				eventList.appendChild(listItem);
		//
		//			});
		//		}


	});
	var modal = document.getElementById('myModal');
	var closeModal = document.getElementsByClassName('close')[0];



	closeModal.addEventListener('click', function() {
		modal.style.display = 'none';
	});

	window.addEventListener('click', function(event) {
		if (event.target === modal) {
			modal.style.display = 'none';
		}
	});
});