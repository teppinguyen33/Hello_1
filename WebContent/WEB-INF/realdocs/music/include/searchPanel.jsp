<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form:form action="/Hello/music/search" method="GET"
	commandName="songSearchParam">
	<form:input path="songName" />
	<input type="submit" value="OK">
</form:form>