<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div>
	<div class="banner-box bg-primary w-100">
		<ul id="banner-list" class="nav h-100">
			<li class="nav-item"><img src="/static/img/dog1.jpg" class="banner"></li>
			<li class="nav-item"><img src="/static/img/dog2.jpg" class="banner"></li>
			<li class="nav-item"><img src="/static/img/dog3.jpg" class="banner"></li>
			<li class="nav-item"><img src="/static/img/dog4.jpg" class="banner"></li>
		</ul>
	</div>
	
	<jsp:include page="../suggestion/recent.jsp" />
	
	<jsp:include page="../suggestion/recommend.jsp" />
</div>

<script>
	$(document).ready(function() {
		/* let banners = ["/static/img/dog1.jpg", "/static/img/dog2.jpg", "/static/img/dog3.jpg", "/static/img/dog4.jpg"]
        let index = 0;
        setInterval(function() {
            index++;
            if (index >= banners.length) {
                index = 0;
            }
            $("#banner").attr("src", banners[index]);
        }, 3000);
        */
        
		let w = $(".banner-box").width();
        
		$("#banner-list").css("width", w * 4);
		$("#banner-list li").css("width", w);
		$("#banner-list .banner").css("width", w);
		
 		$("#banner-list li").last().prependTo("#banner-list");
		$("#banner-list").css("left", 0 - w);
        
        setInterval(function() {
        	$("#banner-list").animate(
        		{"left":"-=" + w}
        		,"slow"
        		,function() {
        			$("#banner-list li").first().appendTo("#banner-list");
        			$("#banner-list").css("left", 0 - w);
        		}
        	)
        }, 3000)
	})
</script>