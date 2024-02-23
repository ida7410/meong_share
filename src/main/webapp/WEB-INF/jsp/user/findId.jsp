<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="p-4 d-flex justify-content-center w-100">
	<div class="col-3 pb-5">
		<h2 class="text-center font-weight-bold mt-5 mb-4">아이디 찾기</h2>
		
		<h5 class="font-weight-bold">이름</h5>
		<input type="text" id="name" class="form-control">
	
		<h5 class="font-weight-bold mt-3">이메일</h5>
		<input type="text" id="email" class="form-control">
			
		<button id="find-id-btn" type="button" class="btn btn-primary form-control mt-4 my-2">아이디 찾기</button>
	</div>
</div>

<script>
	$(document).ready(function() {
		$("#find-id-btn").on("click", function() {
			let name = $("#name").val().trim();
			let email = $("#email").val().trim();
			
			if (!name) {
				alert("이름을 입력해주세요.");
				return;
			}
			if (!email) {
				alert("이메일을 입력해주세요.");
				return;
			}
			
			console.log(name + email)
			
			$.ajax({
				type:"post"
				,url:"/user/findId"
				,data:{"name":name, "email":email}
				
				,success:function(data) {
					if (data.code == 200) {
						alert(data.loginId);
						location.href = "/log-in";
					}
					else if(data.code == 300) {
						alert(data.message);
						location.reload();
					}
				}
			})
		});
	})
</script>