<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7721e3cb346c5be67ed5ff4096c78f53&libraries=services,clusterer,drawing"></script>
<div id="map" style="width: 500px; height: 400px;"></div>

<script>
	$(document).ready(function() {
		var container = document.getElementById('map');
		var options = {
			center : new kakao.maps.LatLng(33.450701, 126.570667),
			level : 3
		};

		var map = new kakao.maps.Map(container, options);
		
		// 중심 좌표나 확대 수준이 변경되면 발생한다.
		kakao.maps.event.addListener(map, 'idle', function() {
		    // do something

			var bounds = map.getBounds();
			console.log(bounds);
			
			let swLatLng = bounds.getSouthWest();
			let neLatLng = bounds.getNorthEast();
			console.log(neLatLng);
			/* $.ajax({
				url:"/transcoord"
				,data:{"x":swLatLng.Ma, "y":swLatLng.Ma}
			}) */
			var geocoder = new kakao.maps.services.Geocoder(), // 좌표계 변환 객체를 생성합니다
				wtmX = swLatLng.Ma, // 변환할 WTM X 좌표 입니다
				wtmY = swLatLng.La; // 변환할 WTM Y 좌표 입니다
	
			// WTM 좌표를 WGS84 좌표계의 좌표로 변환합니다
			geocoder.transCoord(wtmX, wtmY, transCoordCB, {
				input_coord: kakao.maps.services.Coords.WTM, // 변환을 위해 입력한 좌표계 입니다
				output_coord: kakao.maps.services.Coords.WGS84 // 변환 결과로 받을 좌표계 입니다 
			});

			// 좌표 변환 결과를 받아서 처리할 콜백함수 입니다.
			function transCoordCB(result, status) {

			    // 정상적으로 검색이 완료됐으면 
			    if (status === kakao.maps.services.Status.OK) {

			        // 마커를 변환된 위치에 표시합니다
			        var marker = new kakao.maps.Marker({
			            position: new kakao.maps.LatLng(result[0].y, result[0].x), // 마커를 표시할 위치입니다
			            map: map // 마커를 표시할 지도객체입니다
			        })
			    }
			}
			console.log(output_coord);
			/* var i;
			for (i = 0; i < data.length; i++) {
				let x = data[i].x;
				let y = data[i].y;
				if (x >= swLatLng.La && x <= neLatLng.La && y >= swLatLng.Ma && y <= neLatLng.Ma) {
					console.log(data[i].sitewhladdr);
				}
			} */
		});
	});
</script>