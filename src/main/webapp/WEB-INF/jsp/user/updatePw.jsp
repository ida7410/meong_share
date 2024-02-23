<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<div class="p-4 d-flex justify-content-center w-100">
	<div class="col-3">
		<h2 class="text-center font-weight-bold mt-5 mb-4">비밀번호 변경</h2>
		
		<h5 class="font-weight-bold">아이디</h5>
		<input type="text" id="id" class="form-control">
		
		<h5 class="font-weight-bold">현재 비밀번호</h5>
		<input type="password" id="password" class="form-control">

		<h5 class="font-weight-bold mt-3">새로운 비밀번호</h5>
			<input type="password" id="new-password" class="form-control">
		
		<h5 class="font-weight-bold mt-3">비밀번호 확인</h5>
		<input type="password" id="password-check" class="form-control">
			
		<button id="update-pw-btn" type="submit" class="btn btn-primary form-control mt-4 my-2">비밀번호 업데이트</button>
		
	</div>
</div>


<script>
	$(document).ready(function() {
		$("#update-pw-btn").on("click", function() {
			let id = $("#id").val().trim();
			let pw = $("#password").val().trim();
			let pw_n = $("#new-password").val().trim();
			let pw_c = $("#password-check").val().trim();
			
			if (!id) {
				alert("아이디를 입력해주세요.");
				return;
			}
			if (!pw || !pw_n || !pw_c) {
				alert("비밀번호를 입력해주세요.");
				return;
			}
			if (pw_n != pw_c) {
				alert("비밀번호가 일치하지 않습니다.");
				return;
			}
			
			$.ajax({
				type:"post"
				,url:"/user/updatePw"
				,data:{"id":id, "password":pw, "new-password":pw_n}
				
				,success:function(data) {
					if (data.code == 200) {
						alert("비밀번호를 업데이트 했습니다. 로그인해주세요.");
						location.href = "/log-in";
					}
					else {
						alert(data.error_message);
					}
				}
				,error:function(request, status, error) {
					alert("비밀번호 업데이트에 실패했습니다. 관리자에게 문의해주세요.");
				}
			});
		});
	});
</script>