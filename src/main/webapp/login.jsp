<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<link rel="stylesheet" href="w3.css">
<html>
<head>
<title>OpossumBlog</title>
<script src="/js/validation.js"></script>
</head>
<body class="w3-light-gray">
	<div class="w3-container w3-light-blue w3-opacity w3-right-align">
		<h1>OpossumBlog</h1>
	</div>
	<div class="w3-container w3-blue">coolline</div>
	<div class="w3-container w3-display-middle" style="width: 400px">
		<div class="w3-card">
			<div class="w3-bar w3-blue">
				<button id="loginTab" class="w3-bar-item w3-button "
					onclick="setTab('login')">LogIn</button>
				<button id="signupTab" class="w3-bar-item w3-button"
					onclick="setTab('signup')">SignUp</button>
			</div>
			<div id="signup" class="w3-light-blue w3-container"
				style="display: none">
				<form method="post"
					action="${pageContext.request.contextPath}/signup"
					class="w3-selection w3-padding">
					
					<h2 class="w3-center">Sign Up</h2>

					<% List<String> violations = (List<String>)request.getAttribute("violations"); %>
					<c:if test="${violations != null}">
						<div class="w3-container w3-panel w3-red">
							<ul>
								<c:forEach items="${violations}" var="item">
									<li>${item}</li>
								</c:forEach>
							</ul>
						</div>
					</c:if>

					<p>
						<label>Login <input required name="login" type="text"
							class="w3-input w3-round">
						</label>
					<p>
						<label>Password <input required name="password"
							type="text" class="w3-input w3-round">
						</label>
					<p>
						<label>Password confirmation <input required
							name="passwordConfirmation" type="text" class="w3-input w3-round">
						</label>
					<div class="w3-center w3-padding">
						<button onclick="submit"
							class="w3-button w3-blue w3-large w3-round">Submit</button>
					</div>
				</form>
			</div>
			<div id="login" class="w3-light-blue" style="display: block">
				<form method="post" action="/login" class="w3-selection w3-padding">
					<div>
						<h1 class="w3-center">Log in</h1>
					</div>

					<p>
						<label>Login <input required name="login" type="text"
							class="w3-input">
						</label>
					<p>
						<label>Password <input required name="password"
							type="text" class="w3-input">
						</label>
					<div class="w3-center w3-padding">
						<button onclick="submit" name="submit" value="login"
							class="w3-button w3-blue w3-large w3-round">Submit</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script>
        function setTab(tabName){
            var x = document.getElementById("signup");
            x.style.display = "none";
            x = document.getElementById("login");
            x.style.display = "none";
            x = document.getElementById(tabName);
            x.style.display = "block";
        }
    </script>

</body>

</html>
