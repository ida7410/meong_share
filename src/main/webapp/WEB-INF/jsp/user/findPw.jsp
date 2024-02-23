<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="p-4 d-flex justify-content-center w-100">
	<div class="col-3 pb-5">
		<h2 class="text-center font-weight-bold mt-5 mb-4">비밀번호 찾기</h2>
		
		<h5 class="font-weight-bold">아이디</h5>
		<input type="text" id="id" class="form-control">
	
		<h5 class="font-weight-bold mt-3">이메일</h5>
		<input type="text" id="email" class="form-control">
			
		<button id="find-pw-btn" type="button" class="btn btn-primary form-control mt-4 my-2">아이디 찾기</button>
	</div>
</div>

<script>
	$(document).ready(function() {
		$("#find-pw-btn").on("click", function() {
			let id = $("#id").val().trim();
			let email = $("#email").val().trim();
			
			if (!id) {
				alert("아이디를 입력해주세요.");
				return;
			}
			if (!email) {
				alert("이메일을 입력해주세요.");
				return;
			}
			
			console.log(name + email)
			
			$.ajax({
				type:"post"
				,url:"/user/findPw"
				,data:{"id":id, "email":email}
				
				,success:function(data) {
					if (data.code == 200) {
						alert("임시 비밀번호가 이메일로 발송되었습니다. 확인 후 비밀번호를 업데이트해주세요.");
						location.href = "/update-pw";
					}
					else if(data.code == 300) {
						alert(data.message);
						location.reload();
					}
				}
				,error:function(request, status, error) {
					alert("비밀번호 찾기에 실패했습니다. 관리자에게 문의해주세요.");
				}
			})
		});
	})
</script>