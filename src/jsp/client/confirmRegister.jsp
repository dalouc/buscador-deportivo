<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirm Registration - Sporting Activities</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<jsp:include page="/jsp/common/banner.jsp"/>
<div class="container">

    <h1>Confirm Your Registration</h1>
    <p>Please verify the following information is correct:</p>

    <% String login   = (String) request.getAttribute("login"); %>
    <% String name    = (String) request.getAttribute("name"); %>
    <% String surname = (String) request.getAttribute("surname"); %>
    <% String address = (String) request.getAttribute("address"); %>
    <% String phone   = (String) request.getAttribute("phone"); %>

    <div class="data-summary">
        <p><strong>Login:</strong> <%= login %></p>
        <p><strong>First Name:</strong> <%= name %></p>
        <p><strong>Surname:</strong> <%= surname %></p>
        <p><strong>Address:</strong> <%= address %></p>
        <p><strong>Phone:</strong> <%= phone %></p>
    </div>

    <p>Is this information correct?</p>

    <!-- YES: confirm and save to database -->
    <form action="<%=request.getContextPath()%>/register" method="POST" style="display:inline;">
        <input type="hidden" name="action" value="confirm">
        <input type="hidden" name="confirm" value="yes">
        <input type="submit" value="Yes, register me" class="btn btn-success">
    </form>

    <!-- NO: go back to registration form (pre-filled via cookies) -->
    <form action="<%=request.getContextPath()%>/register" method="POST" style="display:inline;">
        <input type="hidden" name="action" value="confirm">
        <input type="hidden" name="confirm" value="no">
        <input type="submit" value="No, let me edit" class="btn btn-secondary">
    </form>

</div>
<jsp:include page="/jsp/common/footer.jsp"/>
</body>
</html>
