<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="d-flex">
	<div class="col-3 chat-list-box p-0 border-right">
	<c:forEach items="${chatListCardList}" var="chatListCard">
		<div class="chat-list d-flex p-3 pointer bg-hover" data-chat-list-id="${chatListCard.cl.id}">
			<div class="col-2 chat-list-img-box">
				<img src="${chatListCard.product.imagePath}" class="crop-img" width="100%">
			</div>
			<div class="col-9">
				<h5 class="font-weight-bold">${chatListCard.product.name}</h5>
				<h6>
				<c:if test="${chatListCard.latestCM.type == 'image'}">
					사진
				</c:if>
				<c:if test="${chatListCard.latestCM.type == 'endTradeRequest'}">
					거래 완료 신청
				</c:if>
				<c:if test="${fn:length(chatListCard.latestCM.message) > 28}">
					${fn:substring(chatListCard.latestCM.message, 0, 28)}...
				</c:if>
				
				<c:if test="${fn:length(chatListCard.latestCM.message) <= 28}">
					${chatListCard.latestCM.message}
				</c:if>
				</h6>
			</div>
		</div>
	</c:forEach>
	</div>

	<div id="chat-box" class="col-9 px-4 pb-4">
		<div class="d-flex justify-content-center text-center py-3 border-bottom">
			<div class="col-2">
				<div class="chat-product-img-box pointer">
					<img src="${chatCard.product.imagePath}" class="crop-img" width="100%">
				</div>
				<h5 class="font-weight-bold mt-3 mb-2 pointer">
					<a href="/product/${chatCard.product.id}">
						${chatCard.product.name}
					</a>
				</h5>
		
				<h6 class="mb-2">
					<c:if test="${userId == chatCard.product.ownerId}">
					<a href="/user/${chatCard.buyer.loginId}">${chatCard.buyer.nickname}</a>
					</c:if>
					
					<c:if test="${userId != chatCard.product.ownerId}">
					<a href="/user/${chatCard.owner.loginId}">${chatCard.owner.nickname}</a>
					</c:if>
				</h6>
				
				<c:if test="${userId == chatCard.product.ownerId && chatCard.product.completed == false}">
				<button type="button" id="end-trade-btn" class="btn btn-primary">거래완료</button>
				</c:if>
			</div>
		</div>
		
		<div id="chat-area-div">
			<jsp:include page="chatBox.jsp" />
		</div>
		
		<div id="chat-input-box" class="input-group input-group-lg pt-3">
			<div id="chat-input-prepend" class="input-group-prepend">
				<button type="button" id="chat-image-btn" class="btn">
					<img src="/static/img/image.jpg" width="100%">
				</button>
				<input type="file" id="chat-image" class="d-none" accept=".jpg, .jpeg, .gif, .png">
			</div>
			<input type="text" id="chat-input" class="form-control">
			<div class="input-group-append">
				<button type="button" id="send-btn" class="btn btn-light">전송</button>
			</div>
		</div>
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm modal-dialog-centered">
		<div class="modal-content text-center">
			<div id="preview-box" class="w-100">
				<img src="" id="preview" class="crop-img" width="100%">
			</div>
			<div class="border-bottom py-3">
				<div id="chat-image-send-btn" class="pointer">전송</div>			
			</div>
			<div class="py-3">
				<div data-dismiss="modal" class="pointer">취소</div>			
			</div>
		</div>
	</div>
</div>


<script>
	$(document).ready(function() {
		$('#chat-area-div').scrollTop($('#chat-area-div')[0].scrollHeight);
		
		let chatListId = ${chatListId};
		let completed = ${chatCard.product.completed}
		
		if (completed) {
			$("#chat-input").attr("placeholder", "거래가 완료되어 채팅이 불가능합니다.");
			$("#chat-input").attr("disabled", true);
			$("#chat-image-send-btn").prop("disabled", true);
			$("#send-btn").prop("disabled", true);
		}
		
		setInterval(function() {
			$("#chat-area-div").load(location.href + " #chat-area-box");
		}, 3000);
		
		$(".chat-product-img-box").on("click", function() {
			location.href = "/product/" + ${chatCard.product.id}
		})
		
		$(".chat-list").on("click", function() {
			let getChatListId = $(this).data("chat-list-id");
			location.href = "/chat/" + getChatListId;
		})
		
		$("#end-trade-btn").on("click", function() {
			let productId = ${chatCard.product.id};
			
			let buyerId = ${chatCard.buyer.id};
			let ownerId = ${chatCard.owner.id};
			
			$.ajax({
				type:"post"
				,url:"/chat/chatMessage/send"
				,data:{"chatListId":chatListId, "message":"거래완료신청", "type":"endTradeRequest"}
				
				,success:function(data) {
					if (data.code == 200) {
						location.reload();
					}
					else {
						alert(data.error_message);
						if (data.code == 300) {
							location.href = "/log-in";
						}
					}
				}
				,error:function(request, status, error) {
					alert("채팅 전송에 실패했습니다. 관리자에게 문의해주세요.");
				}
			});
		});
		
		$(".complete-trade-btn").on("click", function() {
			let productId = ${chatCard.product.id};
			console.log(productId)
			
			$.ajax({
				type:"put"
				,url:"/product/complete/" + productId
				
				,success:function(data) {
					if (data.code == 200) {
						alert("거래를 완료했습니다.");
						location.reload();
					}
					else {
						alert(data.error_message)
					}
				}
				,error:function(request, status, error) {
					alert("거래 완료 신청에 실패했습니다. 관리자에게 문의해주세요.")
				}
			})
		})
		
		$("#recommend").on("click", function() {
			let subjectId = ${chatCard.owner.id};
			let productId = ${chatCard.product.id};
			
			$.ajax ({
				type:"post"
				,url:"/like"
				,data:{"subjectId":subjectId, "type":productId}
				
				,success:function(data) {
					if (data.code == 200) {
						location.reload();
					}
					else {
						alert(data.error_message);
						if (data.code == 300) {
							location.href = "/log-in";
						}
					}
				}
				,error:function(request, status, error) {
					alert("사용자 추천에 실패했습니다. 관리자에게 문의해주세요.");
				}
			})
		});
		
		$("#not-recommend").on("click", function() {
			
		})
		
		$("#chat-image-btn").on("click", function() {
			$("#chat-image").click();
		})
		
		$("#chat-image").on("change", function(event) {
			var reader = new FileReader();
			
			reader.onload = function(event){
				$("#modal #preview").attr("src", event.target.result);
			};
			
			reader.readAsDataURL(event.target.files[0]);
			
			$("#modal").modal("show");
		});
		
		$("#chat-image-send-btn").on("click", function() {
			console.log("here")
			let fileName = $("#chat-image").val();
			if (!fileName) {
				return;
			}
			
			let formData = new FormData();
			formData.append("chatImageFile", $("#chat-image")[0].files[0]);
			formData.append("chatListId", chatListId);
			formData.append("type", "image");
			
			$.ajax({
				type:"post"
				,url:"/chat/chatMessage/send"
				,data:formData
				,enctype:"multipart/form-data"
				,processData:false
				,contentType:false
				
				,success:function(data) {
					if (data.code == 200) {
						location.reload();
					}
					else {
						alert(data.error_message);
						if (data.code == 300) {
							location.href = "/log-in";
						}
					}
				}
			});
		})
		
		$("#chat-input").on("keydown", function(key) {
			if (key.keyCode == 13) {
				$("#send-btn").click();
			}
		});
		
		$("#send-btn").on("click", function() {
			let message = $("#chat-input").val().trim();
			
			$.ajax({
				type:"POST"
				,url:"/chat/chatMessage/send"
				,data:{"chatListId":chatListId, "message":message, "type":"message"}
			
				,success:function(data) {
					if (data.code == 200) {
						location.reload();
					}
					else {
						alert(data.error_message);
						if (data.code == 300) {
							location.href = "/log-in";
						}
					}
				}
			});
		});
	});
</script>