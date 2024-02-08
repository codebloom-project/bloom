/**
 * 
 */
$(function () {
			weatherLiveInfo();
		});

		function weatherLiveInfo() {
			// 주소-좌표 변환 객체를 생성합니다
			var geocoder = new kakao.maps.services.Geocoder();
			var rs;
			// 주소로 좌표를 검색합니다
			geocoder.addressSearch('상계동', function (result, status) {
				// 정상적으로 검색이 완료됐으면 
				if (status === kakao.maps.services.Status.OK) {
					var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
					console.log("경도" + result[0].x);//경도
					console.log("위도" + result[0].y);//위도

					rs = dfs_xy_conv("toXY", result[0].y, result[0].x);
					console.log("경도" + rs.x);
					console.log("위도" + rs.y);
					$.ajax({
						url: "/data/weather",
						data: rs,
						success: function (resulthtml) {
							$("#weather-live-data").html(resulthtml)
						}
					});
				}
			});
		}
		$(function () {
			weatherDayInfo();
		});

		function weatherDayInfo() {
			// 주소-좌표 변환 객체를 생성합니다
			
			var geocoder = new kakao.maps.services.Geocoder();
			var rs;
			// 주소로 좌표를 검색합니다
			geocoder.addressSearch('상계동', function (result, status) {
				// 정상적으로 검색이 완료됐으면 
				if (status === kakao.maps.services.Status.OK) {
					var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
					console.log("경도" + result[0].x);//경도
					console.log("위도" + result[0].y);//위도

					rs = dfs_xy_conv("toXY", result[0].y, result[0].x);
					console.log("경도" + rs.x);
					console.log("위도" + rs.y);
					$.ajax({
						url: "/data/dayweather",
						data: rs,
						success: function (list) {
							console.log(list);
							//$("#weather-day-data").html(resulthtml)
							
							chart(list);
						}
					});
				}
			});
		}
		$(function () {
			weatherWeekInfo();
		});

		function weatherWeekInfo() {
			// 주소-좌표 변환 객체를 생성합니다
			var geocoder = new kakao.maps.services.Geocoder();
			var rs;
			// 주소로 좌표를 검색합니다
			geocoder.addressSearch('상계동', function (result, status) {
				// 정상적으로 검색이 완료됐으면 
				if (status === kakao.maps.services.Status.OK) {
					var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
					console.log("경도" + result[0].x);//경도
					console.log("위도" + result[0].y);//위도

					rs = dfs_xy_conv("toXY", result[0].y, result[0].x);
					console.log("경도" + rs.x);
					console.log("위도" + rs.y);
					$.ajax({
						url: "/data/weekweather",
						data: rs,
						success: function (resulthtml) {
							$("#weather-week-data").html(resulthtml)
							
						}
					});
				}
			});
		}
		function openWeather(){
					document.querySelector('.highcharts-figure').style.display='block';
				}
				function closeWeather(){
					document.querySelector('.highcharts-figure').style.display='none';
				}
			// Data retrieved https://en.wikipedia.org/wiki/List_of_cities_by_average_temperature
			function chart(list){
				
					//list를 차트의 데이터에 맞게 데이터추출
					const categories=[] //['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
					const data=[]//[16.0, 18.2, 23.1, 27.9, 32.2, 36.4, 39.8, 38.4, 35.5, 29.2,22.0, 17.8];
					list.forEach(item=>{
						//console.log(item.fcstTime,":",item.fcstValue);
						if (item.category === "T1H"){
							categories.push(item.fcstTime);
							data.push(parseFloat(item.fcstValue));
						}
					})
					/////////////////////////////////////
					
					Highcharts.chart('container', {
					    chart: {
					        type: 'line'
					    },
					    title: {
					        text: '오늘 날씨 현황'
					    },
					    xAxis: {
					        categories: categories
					    },
					    yAxis: {
					        title: {
					            text: 'Temperature (°C)'
					        }
					    },
					    plotOptions: {
					        line: {
					            dataLabels: {
					                enabled: true
					            },
					            enableMouseTracking: false
					        }
					    },
					    series: [{
					        name: '날씨',
					        data: data
					    }/*, {
					        name: 'Tallinn',
					        data: [-2.9, -3.6, -0.6, 4.8, 10.2, 14.5, 17.6, 16.5, 12.0, 6.5,
					            2.0, -0.9]
					    }*/]
					});
				}