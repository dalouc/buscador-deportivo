<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manager Dashboard - Sporting Activities</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<jsp:include page="/jsp/common/banner.jsp"/>

<div class="user-bar">
    <span><strong>Manager Panel</strong></span>
    <a href="<%=request.getContextPath()%>/manager/dashboard?action=logout">Log Out</a>
</div>

<div class="container">

    <h1>Manager Dashboard</h1>
    <p>Select an operation:</p>

    <div class="options">
        <div class="option-card">
            <h2>Add Activity</h2>
            <p>Create a new sporting activity.</p>
            <a href="<%=request.getContextPath()%>/manager/dashboard?action=addForm" class="btn btn-success">Add Activity</a>
        </div>
        <div class="option-card">
            <h2>List Activities</h2>
            <p>View and edit all registered activities.</p>
            <a href="<%=request.getContextPath()%>/manager/dashboard?action=list" class="btn btn-primary">List Activities</a>
        </div>
    </div>

</div>
<jsp:include page="/jsp/common/footer.jsp"/>
</body>
</html>
