<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="col-8 pl-5">
	<div id="pw-box" class="d-flex mb-3">
		<h5 class="font-weight-bold col-3 d-flex align-items-center">현재 비밀번호</h5>
		<input type="password" id="og-password" class="form-control col-5">
		<div id="pw-desc" class="small d-flex align-items-center pl-3"></div>
	</div>
	
	<div id="pw-box" class="d-flex mb-3">
		<h5 class="font-weight-bold col-3 d-flex align-items-center">새 비밀번호</h5>
		<input type="password" id="password" class="form-control col-5">
		<div id="pw-desc" class="small d-flex align-items-center pl-3"></div>
	</div>
	
	<div id="pw-check-box" class="d-flex mb-3">
		<h5 class="font-weight-bold col-3 d-flex align-items-center">비밀번호 확인</h5>
		<input type="password" id="passwordCheck" class="form-control col-5">
		<div id="pwc-desc" class="small d-flex align-items-center pl-3"></div>
	</div>
	
	<button id="update-pw-btn" type="button" class="btn btn-primary form-control mt-4">가입하기</button>
</div>


<script>
	$(document).ready(function() {

		let passwordReg = false;
		let passwordChecked = false;
		

		$("#password").on("input", function() {
	        let pw = $("#password").val();
	        let reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;
	        
	        $("#pw-desc").removeClass("text-danger");
	        $("#pw-desc").removeClass("text-success");
	        $("#passwordCheck").val("");
			
	        if (pw.length < 8) {
	        	$("#pw-desc").text("비밀번호를 8자 이상 입력해주세요.");
	        	$("#pw-desc").addClass("text-danger");
	        	
	        	passwordReg = false;
	        	return;
	        }
	        if (pw.search(/\s/) != -1) {
	        	$("#pw-desc").text("비밀번호는 공백 없이 입력해주세요.");
	        	$("#pw-desc").addClass("text-danger");
	        	
	        	passwordReg = false;
	            return;
	        }
	        
	        if (!reg.test(pw)) {
	        	$("#pw-desc").text("비밀번호는 숫자/대문자/소문자/특수문자를 포함해야 합니다.");
	        	$("#pw-desc").addClass("text-danger");
	        	
	        	passwordReg = false;
	        	return;
	        } else {
	        	$("#pw-desc").text("사용 가능한 비밀번호입니다.");
	        	$("#pw-desc").addClass("text-success");
	        	
	        	passwordReg = true;
	        }
		});
		
		$("#passwordCheck").on("input", function() {
			let pw = $("#password").val();
			let pwc = $(this).val();
			
			$("#pwc-desc").removeClass("text-danger");
			
			if (pw != pwc) {
				$("#pwc-desc").text("비밀번호가 일치하지 않습니다.");
				$("#pwc-desc").addClass("text-danger");
				
				passwordChecked = false;
				return;
			}
			else if (passwordReg){
				$("#pwc-desc").text("");
				$("#pwc-desc").removeClass("text-danger");
				
				passwordChecked = true;
			}
		});
		
		$("#update-pw-btn").on("click", function() {
			let og_password = $("#og-password").val();
			let password = $("#password").val().trim();
			let passwordCheck = $("#passwordCheck").val().trim();
			
			if (!og_password || !password || !passwordCheck) {
				alert("비밀번호를 입력해주세요.");
				return;
			}
			if (!passwordChecked) {
				alert("사용 불가능한 비밀번호입니다.");
				return;
			}

			$.ajax({
				type:"post"
				,url:"/user/update-pw"
				,data:{"password":og_password, "newPassword":password}
				
				,success:function(data) {
					if (data.code == 200) {
						alert("비밀번호를 수정했습니다. 다시 로그인해주세요.");
						location.href = "/log-out"
					}
					else {
						alert(data.error_message);
						if (data.code == 300) {
							location.reload();
						}
					}
				}
				,error:function(request, status, error) {
					alert("비밀번호 수정에 실패했습니다. 관리자에게 문의해주세요.");
				}
			});
		})
	})
	
</script>