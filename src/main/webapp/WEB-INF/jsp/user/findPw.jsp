<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="p-4 d-flex justify-content-center w-100">
	<div id="find-pw-box" class="col-3 pb-5">
		<h2 class="text-center font-weight-bold mt-5 mb-4">비밀번호 찾기</h2>
		
		<h5 class="font-weight-bold">이름</h5>
		<input type="text" id="name" class="form-control">
	
		<h5 class="font-weight-bold mt-3">이메일</h5>
		<input type="text" id="email" class="form-control">
	
		<button id="find-pw-btn" type="button" class="btn btn-primary form-control mt-4 my-2">아이디 찾기</button>
	</div>
	
	<div id="check-code-box" class="col-3 pb-5 d-none">
		<h2 class="text-center font-weight-bold mt-5 mb-4">비밀번호 찾기</h2>
		
		<div class="mt-3">
			<h5 class="font-weight-bold mt-3">인증번호</h5>
			<input type="text" id="code" class="form-control">
			<small id="code-desc">인증번호가 전송되었습니다.</small>
		</div>
	
		<button id="check-code-btn" type="button" class="btn btn-primary form-control mt-4 my-2">인증번호 확인</button>
	</div>
	
</div>

<script>
	$(document).ready(function() {
		var randomChar = "";
		
		$("#find-pw-btn").on("click", function() {
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
				,url:"/user/findPw"
				,data:{"name":name, "email":email}
				
				,success:function(data) {
					if (data.code == 200) {
						$("#find-pw-box").addClass("d-none");
						$("#check-code-box").removeClass("d-none");
						randomChar = data.randomChar;
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
		
		$("#check-code-btn").on("click", function() {
			let code = $("#code").val();
			if (!code) {
				alert("인증번호를 입력해주세요.")
				return;
			}
			
			if(code != randomChar) {
				$("#code-desc").val("인증번호가 일치하지 않습니다.")
				return;
			}
			
			alert("here")
		})
		
	})
</script>