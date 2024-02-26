<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<button type="button" id="mail-send-btn">메일전송</button>

<script>
	$(document).ready(function() {
		$("#mail-send-btn").on("click", function() {
			$.ajax({
				type:"post"
				,url:"/mail"
				,data:{"address":"ida.yoonh741@gmail.com", "title":"메일전송테스트", "content":"메일 전송 테스트용"}
				,success:function(data) {
					if (data.code == 200) {
						console.log("success");
					}
				}
			
			});
		});
	});
</script>