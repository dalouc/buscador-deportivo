<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Successful - Sporting Activities</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<jsp:include page="/jsp/common/banner.jsp"/>
<div class="container">

    <h1>Registration Completed Successfully</h1>

    <div class="message message-success">
        <p>Your account has been created successfully.</p>
    </div>

    <% String login = (String) request.getAttribute("login"); %>

    <p>You can now log in with the following credentials:</p>
    <div class="data-summary">
        <p><strong>Login:</strong> <%= login %></p>
        <p><strong>Password:</strong> (the password you provided during registration)</p>
    </div>

    <p>
        <a href="<%=request.getContextPath()%>/login" class="btn btn-primary">Log In Now</a>
        <a href="<%=request.getContextPath()%>/" class="btn btn-secondary">Return to Initial Page</a>
    </p>

</div>
<jsp:include page="/jsp/common/footer.jsp"/>
</body>
</html>
