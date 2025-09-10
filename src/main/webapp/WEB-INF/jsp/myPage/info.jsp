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
			<h5 class="font-weight-bold col-3 d-flex align-items-center">ID</h5>
			<div class="inside-group col-5 p-0">
				<input type="text" id="id" class="form-control">
				<button type="button" id="check-dup-btn" class="inside-btn btn btn-info py-1 px-2">Duplicate</button>
			</div>
			<div id="id-desc" class="small d-flex align-items-center pl-3"></div>
		</div>
		
		<div class="d-flex mb-3">
			<h5 class="font-weight-bold col-3 d-flex align-items-center">Nickname</h5>
			<input type="text" id="nickname" class="form-control col-5">
		</div>
		
		<div class="d-flex mb-3">
			<h5 class="font-weight-bold col-3 d-flex align-items-center">Name</h5>
			<input type="text" id="name" class="form-control col-5">
		</div>
		
		<div class="d-flex mb-3">
			<h5 class="font-weight-bold col-3 d-flex align-items-center">Phonenumber</h5>
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
			<h5 class="font-weight-bold col-3 d-flex align-items-center">Email</h5>
			<div class="inside-group col-5 p-0">
				<input type="text" id="email" name="email" class="form-control">
				<button type="button" id="send-code-btn" class="inside-btn btn btn-info py-1 px-2">Verify</button>
			</div>
			<div id="email-desc" class="small d-flex align-items-center pl-3"></div>
		</div>
		
		<div class="d-flex mb-3">
			<h5 class="font-weight-bold col-3 d-flex align-items-center">Verification Code</h5>
			<div class="inside-group col-5 p-0">
				<input type="text" id="email-check" name="email-check" class="form-control" disabled>
				<button type="button" id="email-check-btn" class="inside-btn btn btn-info py-1 px-2" disabled>Check</button>
			</div>
			<div id="email-check-desc" class="small d-flex align-items-center pl-3"></div>
		</div>
		
				
	</div>
	<button id="update-info-btn" type="button" class="btn btn-primary form-control mt-4">Save</button>
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
				$("#id-desc").text("Please type more than 4 characters for ID.");
				$("#id-desc").addClass("text-danger");
				
				idChecked = false;
				return;
			}
			
			if (!idChecked) {
				$("#id-desc").text("Please check duplicates of ID.");
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
							$("#id-desc").text("Available ID");
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
					alert("Unable to check ID duplicates. Please contact through Customer service.")
				}
			})
		});
		
		$("#update-info-btn").on("click", async function() {
			let loginId = $("#id").val().trim();
			let nickname = $("#nickname").val().trim();
			let name = $("#name").val().trim();
			let phoneNumberFirst = $("#phone-number-first > option:selected").val();
			let phoneNumberSecond = $("#phone-number-second").val().trim();
			let phoneNumberThird = $("#phone-number-third").val().trim();
			let phoneNumber = phoneNumberFirst + phoneNumberSecond + phoneNumberThird;
			let email = $("#email").val().trim();

			if (loginId) {
				if (duplicateId) {
					alert("Please check duplicates of ID.");
					return;
				}
				if (!idChecked) {
					alert("This ID is not usable.");
					return;
				}
			}
			if ((phoneNumberSecond && !phoneNumberThird) || (!phoneNumberSecond && phoneNumberThird)) {
				alert("Please type Phonenumber");
				return;
			}
			
			if (!phoneNumberSecond && !phoneNumberThird) {
				phoneNumber = "";
			}

			// upload file to gcs & get public url
			let file = $("#profileImageFile")[0].files[0];
			let fileName = $("#profileImageFile").val();
			let ext = fileName.split('.')[1];
			let formData = new FormData();
			formData.append("file", file);
			formData.append("key", `${userLoginId}`);
			formData.append("ext", ext);
			formData.append("type", "profile-images");

			const uploadResponse = await fetch("/uploadToGcs", {
				method: "POST",
				body: formData
			});

			const uploadResult = await uploadResponse.json();
			if (uploadResult.code !== 200) {
				alert("Upload failed: " + uploadResult.error);
				return;
			}
			const imageUrl = uploadResult.imageUrl;
			console.log(imageUrl)

			formData = new FormData();
			formData.append("loginId", loginId);
			formData.append("nickname", nickname);
			formData.append("name", name);
			formData.append("phoneNumber", phoneNumber);
			formData.append("email", email);
			formData.append("profileImageFile", imageUrl);

			$.ajax({
				type:"post"
				,url:"/user/update"
				,data:formData
				,processData: false
				,contentType: false
				,success:function(data) {
					if (data.code == 200) {
						alert("Successfully saved the change.");
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