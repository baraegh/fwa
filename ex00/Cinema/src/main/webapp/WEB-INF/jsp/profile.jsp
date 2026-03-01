<%@ page import="com.cinema.models.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
</head>
<body>

<h2>Profile</h2>
<%
    User user = (User) session.getAttribute("user");
    if (user != null) {
%>
    <p> It's Me, <%= user.getFirstName() + " " + user.getLastName() %> </p>
    <p><%= user.getEmail() %></p>
<% } %>

</body>
</html>