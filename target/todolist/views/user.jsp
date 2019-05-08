<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List, com.yakimov.entities.User"%>
<!DOCTYPE html>
<link rel="stylesheet" href="stylesheets/w3.css">
<link rel="stylesheet" href="stylesheets/theme.css">
<html>
<head>
<title>OpossumBlog</title>
<script src="/js/validation.js"></script>
</head>
<body class="w3-theme-l5">
	<div class="w3-card">
		<div class="w3-container w3-theme w3-opacity w3-right-align">
			<h1>OpossumBlog</h1>
		</div>
		<div class="w3-container w3-theme-d2">coolline</div>
	</div>
	<div class="w3-container">
		<%
		    User user = (User) session.getAttribute("user");
		%>
		<c:if test="${user != null}">
			<h1>Welcome!</h1>
			<h5>Your login: <%= user.getLogin() %></h5>
			<h5>Your password: <%= user.getPassword() %></h5>
		</c:if>
	</div>
</body>

</html>