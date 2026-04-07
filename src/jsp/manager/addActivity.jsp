<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="activities.model.Pavillion" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Activity - Manager</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<jsp:include page="/jsp/common/banner.jsp"/>

<div class="user-bar">
    <span><strong>Manager Panel</strong></span>
    <a href="<%=request.getContextPath()%>/manager/dashboard?action=logout">Log Out</a>
</div>

<div class="container">

    <h1>Add New Activity</h1>

    <% String error = (String) request.getAttribute("error");
       if (error != null) { %>
        <div class="message message-error"><%= error %></div>
    <% } %>

    <form action="<%=request.getContextPath()%>/manager/dashboard" method="POST">
        <input type="hidden" name="action" value="add">

        <label for="name">Activity Name:</label>
        <input type="text" id="name" name="name" maxlength="16" required>

        <label for="description">Description:</label>
        <input type="text" id="description" name="description" maxlength="32" required>

        <label for="startDate">Start Date:</label>
        <input type="text" id="startDate" name="startDate" maxlength="16" required>

        <label for="cost">Cost:</label>
        <input type="number" id="cost" name="cost" step="0.01" min="0" required>

        <label for="pavillionName">Pavillion:</label>
        <%
            ArrayList<Pavillion> pavs = (ArrayList<Pavillion>) request.getAttribute("pavillions");
            if (pavs != null && !pavs.isEmpty()) {
        %>
            <select id="pavillionName" name="pavillionName">
                <% for (int i = 0; i < pavs.size(); i++) {
                       Pavillion p = pavs.get(i);
                %>
                    <option value="<%= p.getName() %>"><%= p.getName() %> (<%= p.getLocation() %>)</option>
                <% } %>
            </select>
        <% } else { %>
            <input type="text" id="pavillionName" name="pavillionName" maxlength="16" required>
        <% } %>

        <label for="totalPlaces">Total Places:</label>
        <input type="number" id="totalPlaces" name="totalPlaces" min="0" required>

        <label for="occupiedPlaces">Occupied Places:</label>
        <input type="number" id="occupiedPlaces" name="occupiedPlaces" min="0" value="0" required>

        <br><br>
        <input type="submit" value="Add Activity" class="btn btn-success">
        <a href="<%=request.getContextPath()%>/manager/dashboard" class="btn btn-secondary">Cancel</a>
    </form>

</div>
<jsp:include page="/jsp/common/footer.jsp"/>
</body>
</html>
