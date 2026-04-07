<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="activities.model.Activity" %>
<%@ page import="activities.model.Pavillion" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Results - Sporting Activities</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<jsp:include page="/jsp/common/banner.jsp"/>

<%-- User info bar with logout option --%>
<div class="user-bar">
    <span>
        Logged in as:
        <strong>
            <%= session.getAttribute("clientName") != null ? session.getAttribute("clientName") : "" %>
            <%= session.getAttribute("clientSurname") != null ? session.getAttribute("clientSurname") : "" %>
        </strong>
        (<%= session.getAttribute("login") != null ? session.getAttribute("login") : "" %>)
    </span>
    <a href="<%=request.getContextPath()%>/logout">Log Out</a>
</div>

<div class="container">

<%
    String resultType = (String) request.getAttribute("resultType");

    if ("pavillions".equals(resultType)) {
        // ============================
        // PAVILLION RESULTS
        // ============================
        ArrayList<Pavillion> pavillions = (ArrayList<Pavillion>) request.getAttribute("pavillions");
%>
        <h1>List of All Pavillions</h1>

        <% if (pavillions == null || pavillions.isEmpty()) { %>
            <div class="message message-info">No pavillions found.</div>
        <% } else { %>
            <table>
                <tr>
                    <th>Pavillion Name</th>
                    <th>Location</th>
                </tr>
                <% for (int i = 0; i < pavillions.size(); i++) {
                       Pavillion p = pavillions.get(i);
                %>
                <tr>
                    <td><%= p.getName() %></td>
                    <td><%= p.getLocation() %></td>
                </tr>
                <% } %>
            </table>
            <p><strong>Total:</strong> <%= pavillions.size() %> pavillion(s)</p>
        <% } %>

<%
    } else {
        // ============================
        // ACTIVITY RESULTS
        // ============================
        ArrayList<Activity> activities = (ArrayList<Activity>) request.getAttribute("activities");
%>
        <h1>Activity Search Results</h1>

        <% if (activities == null || activities.isEmpty()) { %>
            <div class="message message-info">No activities match the selected criteria.</div>
        <% } else { %>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Start Date</th>
                    <th>Cost</th>
                    <th>Pavillion</th>
                    <th>Total Places</th>
                    <th>Occupied</th>
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
                </tr>
                <% } %>
            </table>
            <p><strong>Total:</strong> <%= activities.size() %> activity/activities</p>
        <% } %>

<%  } %>

    <p><a href="<%=request.getContextPath()%>/search">Back to Search</a></p>

</div>
<jsp:include page="/jsp/common/footer.jsp"/>
</body>
</html>
