<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=7721e3cb346c5be67ed5ff4096c78f53&libraries=services,clusterer,drawing"></script>

<div class="d-flex">
	<div id="list-box" class="list bg-primary col-3 p-0">
		<div class="d-flex">
			<h4 class="type font-weight-bold p-3" data-type="all">전체</h4>
			<h4 class="type font-weight-bold p-3" data-type="hospital">병원</h4>
			<h4 class="type font-weight-bold p-3" data-type="pharmacy">약국</h4>
		</div>
		<div id="list"></div>
	</div>
	
	<div class="p-3 col-9">
		<div id="map" class="w-100"></div>	
	</div>
</div>

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
		var markers = [];
		var infowindows = [];
		var type = "all";
		
		if (navigator.geolocation) {
		    
		    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
		    navigator.geolocation.getCurrentPosition(function(position) {
		        
		        var lat = position.coords.latitude, // 위도
		            lon = position.coords.longitude; // 경도
		        
		        var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
		            message = '<div style="padding:5px;">여기에 계신가요?!</div>'; // 인포윈도우에 표시될 내용입니다
		        
		        // 마커와 인포윈도우를 표시합니다
		        map.setCenter(locPosition);  
		            
		      });
		    
		} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
		    
		    var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),    
		        message = 'geolocation을 사용할수 없어요..'
		        
		    map.setCenter(locPosition);
		}
		

		function getPosition(vet) {
			position = {"name":vet.name,
						"address":vet.address,
						"type":vet.type,
						"latlng": new kakao.maps.LatLng(vet.y, vet.x)}
			positions.push(position);
			console.log(position);
		}
		
		function removeMarker() {
			console.log(markers);
		    for ( var i = 0; i < markers.length; i++ ) {
		    	markers[i].setMap(null);
		    }   
		    markers = [];
		    console.log("removed");
		}
		
		function getMarker(positions) {
			
			infowindows = [];
			console.log("before")
			console.log(markers);
			removeMarker();
			console.log("after")
			console.log(markers);
			
			var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
		    
			for (var i = 0; i < positions.length; i ++) {
			    console.log(positions[i]);
			    // 마커 이미지의 이미지 크기 입니다
			    var imageSize = new kakao.maps.Size(24, 35); 
			    
			    // 마커 이미지를 생성합니다    
			    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 
			    if (positions[i].type == "hospital") {
			    	// 마커를 생성합니다
				    var marker = new kakao.maps.Marker({
				        map: map, // 마커를 표시할 지도
				        position: positions[i].latlng, // 마커를 표시할 위치
				        image : markerImage // 마커 이미지 
				    });
			    }
			    else {
			    	// 마커를 생성합니다
				    var marker = new kakao.maps.Marker({
				        map: map, // 마커를 표시할 지도
				        position: positions[i].latlng, // 마커를 표시할 위치
				    });
			    }
			    
			    
			 	// 마커에 표시할 인포윈도우를 생성합니다 
				var infowindow = new kakao.maps.InfoWindow({
					content: '<div style="padding:5px;">' + positions[i].name + '</div>' // 인포윈도우에 표시할 내용
				});
			 	infowindows.push(infowindow);
			    
				// 마커에 이벤트를 등록하는 함수 만들고 즉시 호출하여 클로저를 만듭니다
			    // 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
			    (function(marker, infowindow) {
			        // 마커에 mouseover 이벤트를 등록하고 마우스 오버 시 인포윈도우를 표시합니다 
			        kakao.maps.event.addListener(marker, 'mouseover', function() {
			            infowindow.open(map, marker);
			        });

			        // 마커에 mouseout 이벤트를 등록하고 마우스 아웃 시 인포윈도우를 닫습니다
			        kakao.maps.event.addListener(marker, 'mouseout', function() {
			            infowindow.close();
			        });
			    })(marker, infowindow);
				
				markers.push(marker);
			}
			console.log(markers);
		}
		
		function getVetList() {
			vets = [];
			positions = [];
			infowindows = [];
			
			var bounds = map.getBounds();
			
			let swLatLng = bounds.getSouthWest();
			let neLatLng = bounds.getNorthEast();
			
			console.log(type);
			
			$.ajax({
				type:"post"
				,url:"/get-vet-list"
				,data:{"min_x":swLatLng.La + ""
						,"min_y":swLatLng.Ma + ""
						,"max_x":neLatLng.La + ""
						,"max_y":neLatLng.Ma + ""
						,"type":type}
				,success:function(data) {
					$("#list").children().remove();
					let i;
					for (i = 0; i < data.length; i++) {
						vets.push(data[i]);
						getPosition(data[i]);
						let info = 
							'<div class="p-3 pointer vet-info" data-vet-id="' + i + '">' + 
								'<h5 class="font-weight-bold">' + data[i].name + '</h5>' + 
								'<small>' + data[i].address + '</small>' +
							'</div>';
						$("#list").append(info);
					}
					getMarker(positions);
				}
			})
		}
		
		// info에 마우스 hover
		$(document).on("mouseover", ".vet-info", function() {
			let vet_id = $(this).data("vet-id");
			let infowindow = infowindows[vet_id];
			infowindow.open(map, markers[vet_id]);
		});
		
		// info에 mouseout
		$(document).on("mouseout", ".vet-info", function() {
			let vet_id = $(this).data("vet-id");
			let infowindow = infowindows[vet_id];
			infowindow.close();
		});
		
		$(".type").on("click", function() {
			type = $(this).data("type");
			getVetList();
		})
		
		// 드래그가 끝날 때 발생
		kakao.maps.event.addListener(map, 'dragend', getVetList);
		
		// 중심 좌표나 확대 수준이 변경되면 발생한다.
		kakao.maps.event.addListener(map, 'idle', getVetList);
	});
</script>