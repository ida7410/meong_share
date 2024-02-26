<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7721e3cb346c5be67ed5ff4096c78f53&libraries=services,clusterer,drawing"></script>
<script type="text/javascript"
	src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=uaxrj4lheu"></script>
<script src="/static/json/vet.json" type="text/javascript"></script>

<input type="text" id="keyword" value="동물병원">
<button type="button" id="search-btn">검색</button>
<ul id="placesList"></ul>
<div id="pagination"></div>
<div id="map" style="width: 500px; height: 400px;"></div>

<script>
	$(document).ready(function() {
		var mapOptions = {
			center : new naver.maps.LatLng(37.3595704, 127.105399),
			zoom : 10
		};

		var map = new naver.maps.Map('map', mapOptions);

		var client_id = 'KxhYATkbflu3pEkt7KIJ';
		var client_secret = 'ICH7I7TwMi';

		$.ajax({
			url : "https://www.animal.go.kr/front/awtis/shop/hospitalList.do?menuNo=6000000002",
			success : function(data) {
				console.log(JSON.parse(data));
			}
		})

		/* naver.maps.Event.addListener(map, 'scrolldown', function(e) {
			$.ajax({
				url : "/naver"
				,success : function(data) {
					console.log(JSON.parse(data));
				}
			});
		}); */

		/* $.ajax({
			url: "https://map.naver.com/p/api/search/allSearch?query=%EB%8F%99%EB%AC%BC%EB%B3%91%EC%9B%90&type=all&searchCoord=126.86519594606307%3B37.482083999998835&boundary="
			,success: function(data) {
				console.log(data);
			}
		});
		 */
		/* 
		var vet = "[{}]";
		getFirstList();
		
		function getFirstList() {
			let url = "http://openapi.seoul.go.kr:8088/5964576d56696461363576437a4c61/json/LOCALDATA_020301/1/1000/";
			$.ajax({
				url:url
				,success:function(data) {
					vet = data.LOCALDATA_020301.row;
					getSecondList();
				}
			});
		}
		function getSecondList() {
			let url = "http://openapi.seoul.go.kr:8088/5964576d56696461363576437a4c61/json/LOCALDATA_020301/1001/2000/";
			$.ajax({
				url:url
				,success:function(data) {
					vet = vet.concat(data.LOCALDATA_020301.row);
					getLastList();
				}
			});
		}
		function getLastList() {
			let url = "http://openapi.seoul.go.kr:8088/5964576d56696461363576437a4c61/json/LOCALDATA_020301/2001/2105/";
			$.ajax({
				url:url
				,success:function(data) {
					vet = vet.concat(data.LOCALDATA_020301.row);
					console.log(vet);
				}
			});
		}
		
		var container = document.getElementById('map');
		var options = {
			center : new kakao.maps.LatLng(33.450701, 126.570667),
			level : 3
		};
		
		var map = new kakao.maps.Map(container, options);
		
		var control = new kakao.maps.ZoomControl();
		map.addControl(control, kakao.maps.ControlPosition.TOPRIGHT);
		 */
	});
</script>