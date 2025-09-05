<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="col-7">
	<input type="text" id="title" class="form-control mb-3" placeholder="Title">
	<textarea id="content" class="form-control mb-3 text-break" placeholder="Text"></textarea>
	
	<div class="d-flex justify-content-end">
		<button type="button" id="send-mail-btn" class="btn btn-primary">Send</button>
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
				,data:{"title":title, "content":content, "address":"ida.yoonh741@gmail.com"}
				
				,success:function(data) {
					if (data.code == 200) {
						alert("Your concern has been sent! It may takes a while to response.");
						location.reload();
					}
				}
			})
		})
	})
</script>