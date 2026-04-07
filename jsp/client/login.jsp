<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Sporting Activities</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<jsp:include page="/jsp/common/banner.jsp"/>
<div class="container">

    <h1>Client Login</h1>
    <p>Please enter your login and password to access the application.</p>

    <% String error = (String) request.getAttribute("error");
       if (error != null) { %>
        <div class="message message-error"><%= error %></div>
    <% } %>

    <form action="<%=request.getContextPath()%>/login" method="POST">
        <label for="login">Username:</label>
        <input type="text" id="login" name="login" maxlength="16" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" maxlength="16" required>

        <br><br>
        <input type="submit" value="Log In" class="btn btn-primary">
    </form>

    <p><a href="<%=request.getContextPath()%>/">Back to initial page</a></p>

</div>
<jsp:include page="/jsp/common/footer.jsp"/>
</body>
</html>
