<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="p-5">
	<div class="d-flex p-4 bg-danger">
		<div class="col-4 bg-primary mr-5">
			<img src="${card.product.imagePath}" width="100%">
		</div>
		
		<div>
			<h2 class="font-weight-bold">${card.product.name}</h2>
			<div class="d-flex mb-3">
				<div>
					<h6>${card.product.company}</h6>
					<h5>${card.product.price}</h5>
					<img src="#">&nbsp; 찜 12개
				</div>
				
				<div class="ml-5">
					<a href="/user/${card.user.loginId}"><h5>${card.user.name}</h5></a>
					<span>추천 횟수: </span><br>
					<span>거래 횟수: </span>
				</div>
			</div>
			
			<pre id="product-desc">${card.product.description}</pre>
			
			<div>
				구매일: ${card.product.boughtDate}
				<br>
				업로드: ${card.product.createdAt}
			</div>
		</div>
	</div>
	
	<jsp:include page="../suggestion/recommend.jsp" />
</div>