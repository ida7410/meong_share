<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="d-flex h-100">
	<div class="d-flex col-3 align-items-end pb-2">
		<img src="/static/img/chat-icon.png" id="chat-btn" width="8%">
	</div>
	<div class="d-flex col-6 align-items-end justify-content-center">
		<div class="display-4">
			<a href="/home" class="logo">MEONG SHARE</a>
		</div>
	</div>
	<div class="d-flex col-3 align-items-end justify-content-end">
		<div>
			<c:if test="${userId eq null}">
			<div>
				<a href="/log-in">Log in</a> / <a href="/sign-up">Sign Up</a>
			</div>
			</c:if>
			
			<c:if test="${userId ne null}">
			<div>
				<a href="/user/${userLoginId}">Welcome, ${userNickname}!</a> / <a href="/log-out">Log out</a>
			</div>
			</c:if>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		$("#chat-btn").on("click", function() {
			location.href = "/chat";
		})
	})
</script>