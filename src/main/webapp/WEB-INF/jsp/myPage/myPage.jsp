<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="my-page pt-5 px-4">
	<h4 class="font-weight-bold mb-3">마이페이지</h4>
	
	<div class="d-flex">
		<div class="col-2 p-0 border-right">
			<div id="info" class="pointer mb-1">내 정보 수정</div>
			<div id="update-pw" class="pointer mb-1">비밀번호 변경</div>
			<div id="recent-trade" class="pointer mb-1">최근 거래 목록</div>
			<div id="customer-service" class="pointer mb-1">문의하기</div>
		</div>
		
		<section id="my-page-content" class="col-11 pb-5">
			<jsp:include page="info.jsp" />
		</section>
	</div>
</div>

<script>
	$(document).ready(function() {
		$("#info").on("click", function() {
			$.ajax({
				url:"/my-page/info"
				,success:function(data) {
					$("#my-page-content").html(data);
				}
			});
		});
		
		$("#update-pw").on("click", function() {
			$.ajax({
				url:"/my-page/update-pw"
				,success:function(data) {
					$("#my-page-content").html(data);
				}
			});
		});
		
		$("#recent-trade").on("click", function() {
			$.ajax({
				url:"/my-page/recent-trade"
				,success:function(data) {
					$("#my-page-content").html(data);
				}
			});
		});
		
		$("#customer-service").on("click", function() {
			$.ajax({
				url:"/my-page/customer-service"
				,success:function(data) {
					$("#my-page-content").html(data);
				}
			});
		});
	})
</script>