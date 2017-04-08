<!DOCTYPE html>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    <h1>Login</h1>
    <form:form action="/Hello/login" method="POST" commandName="user">
        <p>Username: <form:input path="username"/></p> 
        <p>Password: <form:input path="password"/></p>
        <input type="submit" value="Submit"/>
    </form:form>
</body>
</html>