<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="product-list-box">
	<c:if test="${empty cardList}">
	<div class="display-4 text-secondary pt-4 pl-3 pb-5 mb-5">올라온 상품이 없습니다.</div>
	</c:if>
	
	<c:if test="${not empty cardList}">
	<div class="d-flex flex-wrap">
	<c:forEach items="${cardList}" var="card">
		<div class="product col-3 p-3 pointer" data-product-id="${card.product.id}">
			<!-- img -->
			<div class="product-img-box w-100 mb-3">
				<img src="${card.product.imagePath}" class="crop-img" alt="" width="100%">
			</div>
					
			<!-- title -->
			<h4 class="font-weight-bold">${card.product.name}</h4>
			
			<h5>${card.product.price}원</h5>
			
			<h7>${card.diffTime} 전</h7>
			
			<h6>${card.user.nickname}</h6>
			<h6 class="text-break">
			<c:if test="${fn:length(card.product.description) > 50}">
				${fn:substring(card.product.description, 0, 50)}...
			</c:if>
					
			<c:if test="${fn:length(card.product.description) <= 50}">
				${card.product.description}
			</c:if>
			</h6>
		</div>
	</c:forEach>
	</div>
	
	<nav aria-label="Page navigation">
		<ul class="pagination justify-content-center">
			<li id="prev-btn" class="page-item">
				<a class="page-link" href="?${keywordParam}page=${pm.startPage-1}">Previous</a>
			</li>
			
			<c:forEach begin="${pm.startPage}" end="${pm.endPage}" var="pageNum">
			<li class="page-item">
				<a class="page-link" href="?${keywordParam}page=${pageNum}">${pageNum}</a>
			</li>
			</c:forEach>
			
			<li id="next-btn" class="page-item">
				<a class="page-link" href="?${keywordParam}page=${pm.endPage+1}">Next</a>
			</li>
		</ul>
	</nav>
	</c:if>
</div>

<script>
	$(document).ready(function() {
		if (${pm.prev} == false) {
			$("#prev-btn").addClass("disabled");
		}
		else {
			$("#prev-btn").removeClass("disabled");
		}
		
		if (${pm.next} == false && ${pm.endPage} > 0) {
			$("#next-btn").addClass("disabled");
		}
		else {
			$("#next-btn").removeClass("disabled");
		}
		$(".product").on("click", function() {
			let productId = $(this).data("product-id");
			location.href = "/product/" + productId;
		});
	});
</script>