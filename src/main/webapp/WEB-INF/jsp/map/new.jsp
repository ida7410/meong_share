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
		var vets = [];
		var positions = [];
		
		if (navigator.geolocation) {
		    
		    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
		    navigator.geolocation.getCurrentPosition(function(position) {
		        
		        var lat = position.coords.latitude, // 위도
		            lon = position.coords.longitude; // 경도
		        
		        var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
		            message = '<div style="padding:5px;">여기에 계신가요?!</div>'; // 인포윈도우에 표시될 내용입니다
		        
		        // 마커와 인포윈도우를 표시합니다
		        map.setCenter(locPosition);  
		        getVetList();
		            
		      });
		    
		} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
		    
		    var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),    
		        message = 'geolocation을 사용할수 없어요..'
		        
		    map.setCenter(locPosition);
		    getVetList();
		}
		

		function getPosition(vet) {
			position = {"title":vet.name,
						"latlng": new kakao.maps.LatLng(vet.y, vet.x)}
			positions.push(position)
		}
		
		function getMarker(positions) {
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
		}
		
		function getVetList() {
			vets = [];
			positions = [];
			
			var bounds = map.getBounds();
			console.log(bounds);
			
			let swLatLng = bounds.getSouthWest();
			let neLatLng = bounds.getNorthEast();
			console.log(neLatLng);
			$.ajax({
				type:"post"
				,url:"/get-vet-list"
				,data:{"min_x":swLatLng.La + ""
						,"min_y":swLatLng.Ma + ""
						,"max_x":neLatLng.La + ""
						,"max_y":neLatLng.Ma + ""}
				,success:function(data) {
					let i;
					for (i = 0; i < data.length; i++) {
						vets.push(data[i]);
						getPosition(data[i]);
					}
					getMarker(positions);
				}
			})
		}
		
		// 드래그가 끝날 때 발생
		kakao.maps.event.addListener(map, 'dragend', getVetList);
		
		// 중심 좌표나 확대 수준이 변경되면 발생한다.
		kakao.maps.event.addListener(map, 'idle', getVetList);
	});
</script>