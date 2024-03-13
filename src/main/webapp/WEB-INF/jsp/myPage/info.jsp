<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="col-8 pl-5">
	<div class="d-flex mb-3">
		<div id="update-preview-box" class="mr-3">
			<img src="${userProfileImagePath}" id="preview" class="crop-img" width="100%">
		</div>
		<div class="d-flex justify-content-end align-items-end">
			<input type="file" id="profileImageFile" accept=".png, .jpg, .jpeg, .gif">
		</div>
	</div>
	<div>
		<div class="d-flex mb-3">
			<h5 class="font-weight-bold col-3 d-flex align-items-center">아이디</h5>
			<div class="inside-group col-5 p-0">
				<input type="text" id="id" class="form-control">
				<button type="button" id="check-dup-btn" class="inside-btn btn btn-info py-1 px-2">중복확인</button>
			</div>
			<div id="id-desc" class="small d-flex align-items-center pl-3"></div>
		</div>
		
		<div class="d-flex mb-3">
			<h5 class="font-weight-bold col-3 d-flex align-items-center">닉네임</h5>
			<input type="text" id="nickname" class="form-control col-5">
		</div>
		
		<div class="d-flex mb-3">
			<h5 class="font-weight-bold col-3 d-flex align-items-center">이름</h5>
			<input type="text" id="name" class="form-control col-5">
		</div>
		
		<div class="d-flex mb-3">
			<h5 class="font-weight-bold col-3 d-flex align-items-center">전화번호</h5>
			<div class="d-flex justify-content-between col-5 p-0 ">
				<select id="phone-number-first" class="form-control col-3 mr-2">
					<option selected>010</option>
					<option>011</option>
					<option>016</option>
				</select>
				<input type="text" id="phone-number-second" class="form-control col-4 mr-2">
				<input type="text" id="phone-number-third" class="form-control col-4">
			</div>
		</div>
		
		<div class="d-flex mb-3">
			<h5 class="font-weight-bold col-3 d-flex align-items-center">이메일</h5>
			<div class="inside-group col-5 p-0">
				<input type="text" id="email" name="email" class="form-control">
				<button type="button" id="send-code-btn" class="inside-btn btn btn-info py-1 px-2">인증하기</button>
			</div>
			<div id="email-desc" class="small d-flex align-items-center pl-3"></div>
		</div>
		
		<div class="d-flex mb-3">
			<h5 class="font-weight-bold col-3 d-flex align-items-center">이메일 인증번호</h5>
			<div class="inside-group col-5 p-0">
				<input type="text" id="email-check" name="email-check" class="form-control" disabled>
				<button type="button" id="email-check-btn" class="inside-btn btn btn-info py-1 px-2" disabled>확인</button>
			</div>
			<div id="email-check-desc" class="small d-flex align-items-center pl-3"></div>
		</div>
		
				
	</div>
	<button id="update-info-btn" type="button" class="btn btn-primary form-control mt-4">저장</button>
</div>

<script>
	$(document).ready(function() {
		$("#pw-box").addClass("d-none");
		$("#pw-check-box").addClass("d-none");
		
		let duplicateId = true;
		let idChecked = false;
		
		$("#profileImageFile").on("change", function(event) {
			var reader = new FileReader();
			
			reader.onload = function(event){
				var preview = document.getElementById("preview");
				preview.setAttribute("src", event.target.result);
			};
			
			reader.readAsDataURL(event.target.files[0]);
		});
		
		$("#id").on("input", function() {
			let id = $(this).val();
	
	        $("#id-desc").removeClass("text-danger");
	        $("#id-desc").removeClass("text-success");
			
			if (id.length < 4) {
				$("#id-desc").text("아이디를 4자 이상 입력해주세요.");
				$("#id-desc").addClass("text-danger");
				
				idChecked = false;
				return;
			}
			
			if (!idChecked) {
				$("#id-desc").text("아이디 중복 확인을 실행해주세요.");
				$("#id-desc").addClass("text-danger");
				
				idChecked = false;
				return;
			}
			
		});
		
		$("#check-dup-btn").on("click", function() {
			let id = $("#id").val().trim();
			
			$.ajax({
				type:"post"
				,url:"/user/check-duplicated-id"
				,data:{"id":id}
				
				,success:function(data) {
					if (data.code == 200) {
						duplicateId = data.isDuplicateId;
						if (!duplicateId){
							$("#id-desc").text("사용 가능한 아이디입니다.");
							$("#id-desc").removeClass("text-danger");
							$("#id-desc").addClass("text-success");
							
							idChecked = true;
						}
					}
					else {
						alert(data.error_message);
					}
				}
				,error:function(request, status, error) {
					alert("아이디 중복 확인에 실패했습니다. 관리자에게 문의해주세요.")
				}
			})
		});
		
		$("#update-info-btn").on("click", function() {
			let loginId = $("#id").val().trim();
			let nickname = $("#nickname").val().trim();
			let name = $("#name").val().trim();
			let phoneNumberFirst = $("#phone-number-first > option:selected").val();
			let phoneNumberSecond = $("#phone-number-second").val().trim();
			let phoneNumberThird = $("#phone-number-third").val().trim();
			let phoneNumber = phoneNumberFirst + phoneNumberSecond + phoneNumberThird;
			let email = $("#email").val().trim();
			let fileName = $("#profileImageFile").val();
			
			if (loginId) {
				if (duplicateId) {
					alert("아이디 중복 확인을 실행해주세요.");
					return;
				}
				if (!idChecked) {
					alert("사용 불가능한 아이디입니다.");
					return;
				}
			}
			if ((phoneNumberSecond && !phoneNumberThird) || (!phoneNumberSecond && phoneNumberThird)) {
				alert("전화번호를 입력해주세요.");
				return;
			}
			
			if (!phoneNumberSecond && !phoneNumberThird) {
				phoneNumber = "";
			}
			
			let formData = new FormData();
			formData.append("loginId", loginId);
			formData.append("password", password);
			formData.append("nickname", nickname);
			formData.append("name", name);
			formData.append("phoneNumber", phoneNumber);
			formData.append("email", email);
			formData.append("profileImageFile", $("#profileImageFile")[0].files[0]);
			
			$.ajax({
				type:"post"
				,url:"/user/update"
				,data:formData
				, enctype:"multipart/form-data" // 파일 업로드를 위한 필수 설정
					, processData:false // 파일 업로드를 위한 필수 설정
					, contentType:false //
				
				,success:function(data) {
					if (data.code == 200) {
						alert("회원 정보 수정에 성공했습니다.");
						location.reload();
					}
					else {
						alert(data.error_message);
						if (data.code == 300) {
							location.href = "/log-in"
						}
					}
				}
				,error:function(request, status, error) {
					alert(data.error_message);
				}
			});
		})
	});
</script>