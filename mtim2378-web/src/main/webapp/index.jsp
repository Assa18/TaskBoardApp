<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.bbte.idde.mtim2378.model.Todo" %>

<%
    List<Todo> todos = (List<Todo>) request.getAttribute("todos");
%>

<!DOCTYPE html>
<html>
<body>

<a href="logout">Logout</a>
<hr>
<h2>Todos</h2>

<ul>
<% if (todos != null) { %>
    <% for (Todo t : todos) { %>
        <li>
            <strong><%= t.getTitle() %></strong><br>
            <%= t.getDescription() %><br>
            Done: <%= t.isDone() %><br>
            Deadline: <%= t.getDeadLine() %><br>
            Severity: <%= t.getSeverity() %>
        </li>
        <br>
    <% } %>
<% } else { %>
    <li>No todos found.</li>
<% } %>
</ul>

</body>
</html>
