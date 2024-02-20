<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7721e3cb346c5be67ed5ff4096c78f53&libraries=services,clusterer,drawing"></script>

<div id="map" style="width: 500px; height: 400px;"></div>
<script>
	var container = document.getElementById('map');
	var options = {
		center : new kakao.maps.LatLng(33.450701, 126.570667),
		level : 3
	};

	var map = new kakao.maps.Map(container, options);
	var markerPosition  = new kakao.maps.LatLng(33.450701, 126.570667); 

	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
	    position: markerPosition
	});

	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map);
	
	/* 5964576d56696461363576437a4c61
	http://openapi.seoul.go.kr:8088/(인증키)/xml/LOCALDATA_020301/1/5/ */
</script>
