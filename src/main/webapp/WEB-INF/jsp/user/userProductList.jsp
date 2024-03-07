<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<nav>
	<ul class="nav border-bottom">
		<li class="nav-item pointer"><a id="incompleted" data-user-login-id="${user.loginId}" class="nav-link user-info-nav-link">전체</a></li>
		<li class="nav-item pointer"><a id="completed" data-user-login-id="${user.loginId}" class="nav-link user-info-nav-link">거래 완료</a></li>
	</ul>
</nav>
<div id="user-product-list-box">
	<jsp:include page="../product/productList.jsp" />
</div>

<script>
	$(document).ready(function() {
		let userLoginId = $("#completed").data("user-login-id");
		console.log(userLoginId);
		
		$("#incompleted").on("click", function() {
			console.log("here")
			$.ajax({
				data:{"userLoginId":userLoginId, "completed":false}
				,url:"/user-product-list"
				,success:function(data) {
					$("#user-proudct-list-large-box").html(data);
				}
			})
		})
		
		$("#completed").on("click", function() {
			$.ajax({
				data:{"userLoginId":userLoginId, "completed":true}
				,url:"/user-product-list"
				,success:function(data) {
					$("#user-proudct-list-large-box").html(data);
				}
			})
		})
		
	})
</script>