<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7721e3cb346c5be67ed5ff4096c78f53&libraries=services,clusterer,drawing"></script>
<script src="/static/json/vet.json" type="text/javascript"></script>

<div id="map" style="width: 500px; height: 400px;"></div>
<script>
/* 
	fetch('/static/xml/vet.xml')
	.then((response) => response.text())
	.then((str) => new DOMParser().parseFromString(str, 'application/xml'))
	.then((xml) => {
	  console.log(xml);
	  console.log(xml.querySelector('X').innerHTML);
	});
 */
	/* 5964576d56696461363576437a4c61
	http://openapi.seoul.go.kr:8088/(인증키)/xml/LOCALDATA_020301/1/5/ */

	let mydata = JSON.parse(JSON.stringify(data));
	console.log(mydata);
	console.log(mydata.DATA[1].x)
	
	function vetList(limitX, limitY) {
		
	}
	
	/* var url = 'http://openapi.seoul.go.kr:8088/5964576d56696461363576437a4c61/json/LOCALDATA_020301/1/5?X=185831.959022199';
	
	$.ajax({
		url:url
		,success:function(data) {
			console.log(data);
		}
	}); */

	var container = document.getElementById('map');
	var options = {
		center : new kakao.maps.LatLng(33.450701, 126.570667),
		level : 3
	};

	var map = new kakao.maps.Map(container, options);
	
	var control = new kakao.maps.ZoomControl();
	map.addControl(control, kakao.maps.ControlPosition.TOPRIGHT);
	
	/* var markerPosition  = new kakao.maps.LatLng(33.450701, 126.570667); 

	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
	    position: markerPosition
	});

	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map); */
	
	/* kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
		// 클릭한 위도, 경도 정보
		var latlng = mouseEvent.latLng;
		
		// 마커 이동
		marker.setPosition(latlng);
	    
	    var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
	    message += '경도는 ' + latlng.getLng() + ' 입니다';
	    
	    alert(message);
	}); */
	
	/* // 드래그가 끝날 때 발생
	kakao.maps.event.addListener(map, 'dragend', function() {
	    // do something

		var bounds = map.getBounds();
		console.log(bounds);
	});
	
	// 중심 좌표나 확대 수준이 변경되면 발생한다.
	kakao.maps.event.addListener(map, 'idle', function() {
	    // do something

		var bounds = map.getBounds();
		console.log(bounds);
	}); */
	
	
	
	var positions = [
	    {
	        title: '카카오', 
	        latlng: new kakao.maps.LatLng(33.450705, 126.570677)
	    },
	    {
	        title: '생태연못', 
	        latlng: new kakao.maps.LatLng(33.450936, 126.569477)
	    },
	    {
	        title: '텃밭', 
	        latlng: new kakao.maps.LatLng(33.450879, 126.569940)
	    },
	    {
	        title: '근린공원',
	        latlng: new kakao.maps.LatLng(33.451393, 126.570738)
	    }
	];

	// 마커 이미지의 이미지 주소입니다
	var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
	    
	for (var i = 0; i < positions.length; i ++) {
	    
	    // 마커 이미지의 이미지 크기 입니다
	    var imageSize = new kakao.maps.Size(24, 35); 
	    
	    // 마커 이미지를 생성합니다    
	    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 
	    
	    // 마커를 생성합니다
	    var marker = new kakao.maps.Marker({
	        map: map, // 마커를 표시할 지도
	        position: positions[i].latlng, // 마커를 표시할 위치
	        title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
	        image : markerImage // 마커 이미지 
	    });
	    
		kakao.maps.event.addListener(marker, 'click', function() {
		    alert('marker click!');
		});
	}
	
	
</script>
