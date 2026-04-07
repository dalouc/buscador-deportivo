<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sporting Activities Search Application - UC3M</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<jsp:include page="/jsp/common/banner.jsp"/>
<div class="container">

    <h1>Welcome</h1>
    <p>
        This application allows you to visualize all the sporting activities
        currently available.
    </p>
    <p>Please select an option:</p>

    <div class="options">
        <div class="option-card">
            <h2>Already Registered</h2>
            <p>If you already have an account, log in to access the search application.</p>
            <a href="<%=request.getContextPath()%>/login" class="btn btn-primary">Log In</a>
        </div>
        <div class="option-card">
            <h2>New User</h2>
            <p>Register as a new user to gain access to the application.</p>
            <a href="<%=request.getContextPath()%>/register" class="btn btn-success">Register</a>
        </div>
    </div>

</div>
<jsp:include page="/jsp/common/footer.jsp"/>
</body>
</html>
