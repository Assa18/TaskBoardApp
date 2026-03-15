<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<body>

<h2>Login</h2>

<form method="post" action="login">
    <label>User:</label><br>
    <input type="text" name="username"><br><br>

    <label>Password:</label><br>
    <input type="password" name="password"><br><br>

    <button type="submit">Login</button>
</form>

<% if (request.getAttribute("error") != null) { %>
    <p style="color:red;"><%= request.getAttribute("error") %></p>
<% } %>

</body>
</html>
