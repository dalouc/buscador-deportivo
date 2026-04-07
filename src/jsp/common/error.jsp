<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error - Sporting Activities</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<jsp:include page="/jsp/common/banner.jsp"/>
<div class="container">
    <h1>An error has occurred</h1>
    <div class="message message-error">
        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
            <%= error %>
        <% } else { %>
            An internal error has occurred while executing the application.
        <% } %>
    </div>
    <p><a href="<%=request.getContextPath()%>/">Return to the initial page</a></p>
</div>
<jsp:include page="/jsp/common/footer.jsp"/>
</body>
</html>
