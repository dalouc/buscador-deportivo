<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Activities - Sporting Activities</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<jsp:include page="/jsp/common/banner.jsp"/>

<%-- User info bar with logout option (Exercise 3) --%>
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

    <h1>Sporting Activities Search</h1>
    <p>
        Search for sporting activities across the three campuses of
        Universidad Carlos III de Madrid (Getafe, Legan&eacute;s and Colmenarejo).
    </p>

    <% String error = (String) request.getAttribute("error");
       if (error != null) { %>
        <div class="message message-error"><%= error %></div>
    <% } %>

    <form action="<%=request.getContextPath()%>/search" method="POST">

        <label for="type">Select a search option:</label>
        <select name="type" id="type" onchange="toggleTextField()">
            <option value="all_activities">List all sporting activities</option>
            <option value="all_pavillions">List all pavillions</option>
            <option value="free_places">List activities with free places</option>
            <option value="cost">List activities with free places costing less than...</option>
            <option value="pavillion">List activities with free places at a specific pavillion</option>
            <option value="activity_name">Show information about a specific activity</option>
            <option value="text_search">Search activities by text in name or description</option>
        </select>

        <div id="textFieldContainer" style="display:none;">
            <label for="text1" id="textLabel">Enter value:</label>
            <input type="text" id="text1" name="text1" size="32">
        </div>

        <br>
        <input type="submit" value="Search">
        <input type="reset" value="Clear">
    </form>

</div>

<jsp:include page="/jsp/common/footer.jsp"/>

<script>
    // Show/hide the text input field based on the selected search type
    function toggleTextField() {
        var type = document.getElementById('type').value;
        var container = document.getElementById('textFieldContainer');
        var label = document.getElementById('textLabel');

        if (type === 'all_activities' || type === 'all_pavillions' || type === 'free_places') {
            container.style.display = 'none';
        } else {
            container.style.display = 'block';
            if (type === 'cost') {
                label.textContent = 'Maximum cost:';
            } else if (type === 'pavillion') {
                label.textContent = 'Pavillion name:';
            } else if (type === 'activity_name') {
                label.textContent = 'Activity name:';
            } else if (type === 'text_search') {
                label.textContent = 'Text to search:';
            }
        }
    }
</script>

</body>
</html>
