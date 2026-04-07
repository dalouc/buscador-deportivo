<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - Sporting Activities</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<jsp:include page="/jsp/common/banner.jsp"/>
<div class="container">

    <h1>Client Registration</h1>
    <p>Please fill in the following details to create your account.</p>

    <% String error = (String) request.getAttribute("error");
       if (error != null) { %>
        <div class="message message-error"><%= error %></div>
    <% } %>

    <%-- Pre-fill fields from request attributes (set by servlet from cookies) --%>
    <% String login   = (String) request.getAttribute("login");   if (login == null)   login = ""; %>
    <% String name    = (String) request.getAttribute("name");    if (name == null)    name = ""; %>
    <% String surname = (String) request.getAttribute("surname"); if (surname == null) surname = ""; %>
    <% String address = (String) request.getAttribute("address"); if (address == null) address = ""; %>
    <% String phone   = (String) request.getAttribute("phone");   if (phone == null)   phone = ""; %>

    <form action="<%=request.getContextPath()%>/register" method="POST">
        <input type="hidden" name="action" value="register">

        <label for="login">Username:</label>
        <input type="text" id="login" name="login" value="<%= login %>" maxlength="16" required>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" maxlength="16" required>

        <label for="name">First Name:</label>
        <input type="text" id="name" name="name" value="<%= name %>" maxlength="16" required>

        <label for="surname">Surname:</label>
        <input type="text" id="surname" name="surname" value="<%= surname %>" maxlength="16" required>

        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="<%= address %>" maxlength="16" required>

        <label for="phone">Phone Number:</label>
        <input type="text" id="phone" name="phone" value="<%= phone %>" maxlength="16" required>

        <br><br>
        <input type="submit" value="Register" class="btn btn-primary">
        <input type="reset" value="Clear">
    </form>

    <p><a href="<%=request.getContextPath()%>/">Back to initial page</a></p>

</div>
<jsp:include page="/jsp/common/footer.jsp"/>
</body>
</html>
