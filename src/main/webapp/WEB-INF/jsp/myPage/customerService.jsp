<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="col-7">
	<input type="text" id="title" class="form-control mb-3" placeholder="제목">
	<textarea id="content" class="form-control mb-3 text-break" placeholder="문의내용"></textarea>
	
	<div class="d-flex justify-content-end">
		<button type="button" id="send-mail-btn" class="btn btn-primary">전송</button>
	</div>
</div>


<script>
	$(document).ready(function() {
		$("#send-mail-btn").on("click", function() {
			let title = $("#title").val().trim();
			let content = $("#content").val().trim();
			
			$.ajax({
				type:"post"
				,url:"/mail"
				,data:{"title":"[Meong Share] 문의: " + title, "content":content, "address":"ida.yoonh741@gmail.com"}
				
				,success:function(data) {
					if (data.code == 200) {
						alert("문의가 정상적으로 전송되었습니다. 답변에 시간이 조금 걸릴 수 있습니다.");
						location.reload();
					}
				}
			})
		})
	})
</script>