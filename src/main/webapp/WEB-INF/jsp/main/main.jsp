<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div>
	<div class="banner-box bg-primary">
		<img id="banner" src="/static/img/dog1.jpg" class="crop-img" width="100%">
	</div>
	
	<jsp:include page="../suggestion/recent.jsp" />
	
	<jsp:include page="../suggestion/recommend.jsp" />
</div>

<script>
	$(document).ready(function() {
		let banners = ["/static/img/dog1.jpg", "/static/img/dog2.jpg", "/static/img/dog3.jpg", "/static/img/dog4.jpg"]
        let index = 0;
        setInterval(function() {
            index++;
            if (index >= banners.length) {
                index = 0;
            }
            $("#banner").attr("src", banners[index]);
        }, 3000);
        
	})
</script>