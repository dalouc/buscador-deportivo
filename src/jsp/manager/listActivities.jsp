<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="activities.model.Activity" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>List Activities - Manager</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<jsp:include page="/jsp/common/banner.jsp"/>

<div class="user-bar">
    <span><strong>Manager Panel</strong></span>
    <a href="<%=request.getContextPath()%>/manager/dashboard?action=logout">Log Out</a>
</div>

<div class="container">

    <h1>All Activities</h1>

    <% String message = (String) request.getAttribute("message");
       if (message != null) { %>
        <div class="message message-success"><%= message %></div>
    <% } %>

    <%
        ArrayList<Activity> activities = (ArrayList<Activity>) request.getAttribute("activities");
        if (activities == null || activities.isEmpty()) {
    %>
        <div class="message message-info">No activities found in the database.</div>
    <% } else { %>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Start Date</th>
                <th>Cost</th>
                <th>Pavillion</th>
                <th>Total</th>
                <th>Occupied</th>
                <th>Actions</th>
            </tr>
            <% for (int i = 0; i < activities.size(); i++) {
                   Activity a = activities.get(i);
            %>
            <tr>
                <td><%= a.getId() %></td>
                <td><%= a.getName() %></td>
                <td><%= a.getDescription() %></td>
                <td><%= a.getStartDate() %></td>
                <td><%= a.getCost() %></td>
                <td><%= a.getPavillionName() %></td>
                <td><%= a.getTotalPlaces() %></td>
                <td><%= a.getOccupiedPlaces() %></td>
                <td>
                    <a href="<%=request.getContextPath()%>/manager/dashboard?action=editForm&id=<%= a.getId() %>"
                       class="btn btn-primary" style="padding:4px 10px; font-size:0.85em;">Edit</a>
                </td>
            </tr>
            <% } %>
        </table>
        <p><strong>Total:</strong> <%= activities.size() %> activity/activities</p>
    <% } %>

    <p>
        <a href="<%=request.getContextPath()%>/manager/dashboard?action=addForm" class="btn btn-success">Add New Activity</a>
        <a href="<%=request.getContextPath()%>/manager/dashboard" class="btn btn-secondary">Back to Dashboard</a>
    </p>

</div>
<jsp:include page="/jsp/common/footer.jsp"/>
</body>
</html>
