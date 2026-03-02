<%@ page import="com.cinema.models.User" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body>

<%
    User user = (User) session.getAttribute("user");
    if (user != null) {
%>

<div class="profile-card">

    <div class="avatar-section">
        <div class="avatar-box">
            <div class="avatar-placeholder">
                <svg viewBox="0 0 24 24" fill="none" stroke="#999" stroke-width="1.2">
                    <rect x="3" y="3" width="18" height="18" rx="2"/>
                    <circle cx="12" cy="9" r="3"/>
                    <path d="M6 21c0-3.314 2.686-6 6-6s6 2.686 6 6"/>
                </svg>
            </div>
        </div>
        <button class="btn-upload">Upload</button>
    </div>

    <div class="profile-info">
        <p class="profile-name">It's Me, <%= user.getFirstName() + " " + user.getLastName() %></p>
        <p class="profile-email"><%= user.getEmail() %></p>

        <table>
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Time</th>
                    <th>IP Address</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="entry" items="${sessionScope.loginEntries}">
                    <tr>
                        <td>${entry.dateFormatted}</td>
                        <td>${entry.timeFormatted}</td>
                        <td>${entry.ipAddress}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</div>

<div class="files-card">
    <table>
        <thead>
            <tr>
                <th>File name</th>
                <th>Size</th>
                <th>MIME</th>
            </tr>
        </thead>
        <tbody>
            <%-- populate with your files list e.g:
            <c:forEach var="file" items="${sessionScope.user.files}">
                <tr>
                    <td><a href="${file.url}">${file.name}</a></td>
                    <td>${file.size}</td>
                    <td>${file.mime}</td>
                </tr>
            </c:forEach>
            --%>
        </tbody>
    </table>
</div>

<% } %>

</body>
</html>