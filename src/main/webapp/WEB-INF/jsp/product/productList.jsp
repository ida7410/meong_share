<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		<div class="d-flex flex-wrap">
			<c:forEach items="${cardList}" var="card">
			<div class="product bg-primary col-3 p-3 pointer" data-product-id="${card.product.id}">
				<!-- img -->
				<div class="product-img-box w-100 bg-info mb-3">
					<img src="${card.product.imagePath}" alt="" width="100%">
				</div>
				
				<!-- title -->
				<h4 class="font-weight-bold">${card.product.name}</h4>
				
				<h5>${card.product.price}원</h5>
				
				<h7>${card.diffTime} 전</h7>
				
				<h6>${card.user.nickname}</h6>
				<h6>${card.product.description}</h6>
			</div>
			</c:forEach>
		</div>

		<nav aria-label="Page navigation example">
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
		
	});
</script>