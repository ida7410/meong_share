<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="d-flex pt-5">
	
	<div class="col-2 pl-4">
		<h4 class="font-weight-bold">상품 검색</h4>
		<form method="GET" action="/search" id="search-form">
			<div class="input-group mb-5">
				<input type="text" id="keyword" name="keyword" class="form-control" value="${keyword}">
				<div class="input-group-append">
					<button id="search-btn" type="submit" class="btn btn-light">검색</button>
				</div>
			</div>
		</form>
		
		<h4 class="font-weight-bold">최근 검색 목록</h4>
		<div class="mb-5">
		<c:forEach items="${keywordList}" var="keyword">
			<a href="/search?keyword=${keyword}" class="keywords">${keyword}</a><br>
		</c:forEach>
		</div>
		
		<h4 class="font-weight-bold">최근 검색 목록</h4>
		<div class="mb-5 col-9 p-0">
			<div class="recent-search-product bg-primary p-2">
				<div class="recent-search-product-img-box w-100 bg-danger mb-2">
					<img src="">
				</div>
				<h5 class="font-weight-bold">제목</h5>
			</div>
			<div class="recent-search-product bg-primary p-2">
				<div class="recent-search-product-img-box w-100 bg-danger mb-2">
					<img src="">
				</div>
				<h5 class="font-weight-bold">제목</h5>
			</div>
			<div class="recent-search-product bg-primary p-2">
				<div class="recent-search-product-img-box w-100 bg-danger mb-2">
					<img src="">
				</div>
				<h5 class="font-weight-bold">제목</h5>
			</div>
		</div>
	</div>
	
	<div class="col-10">
		<jsp:include page="productList.jsp" />
	</div>
	
	<div id="temp">${total_count }</div>
</div>


<script>
	$(document).ready(function() {
		
		const getCookieValue = (name) => (
			document.cookie.match('(^|;)\\s*' + name + '\\s*=\\s*([^;]+)')?.pop() || ''
		)
		/* 
		function getCookie(cookie_name) {
			var x, y;
			var val = document.cookie.split(';');
			
			for (var i = 0; i < val.length; i++) {
				x = val[i].substr(0, val[i].indexOf('='));
				y = val[i].substr(val[i].indexOf('=') + 1);
				x = x.replace(/^\s+|\s+$/g, ''); // 앞과 뒤의 공백 제거하기
				
				if (x == cookie_name) {
					return unescape(y); // unescape로 디코딩 후 값 리턴
				}
			}
		}
		var items = getCookie('productItems');
		console.log(items);
		$("#temp").text(items);
		
		function addCookie(id) {
			var maxItemNum = 5; // 최대 저장 가능한 아이템개수
			var expire = 7; // 쿠키값을 저장할 기간
			if (items) {
			var itemArray = items.split(',');
			if (itemArray.indexOf(id) != -1) {
				// 이미 존재하는 경우 종료
				console.log('Already exists.');
			}
			else {
				// 새로운 값 저장 및 최대 개수 유지하기
				itemArray.unshift(id);
				if (itemArray.length > maxItemNum ) itemArray.length = 5;
					items = itemArray.join(',');
					setCookie('productItems', items, expire);
				}
			}
			else {
			// 신규 id값 저장하기
			setCookie('productItems', id, expire);
			}
		} */
		
		$(".product").on("click", function() {
			let productId = $(this).data("product-id");
			location.href = "/product/" + productId;
		});
		/* 
		$("#search-form").on("submit", function(e) {
			let keyword = $("#keyword").val().trim();
			addCookie(keyword);
		});	 */	
	})
</script>