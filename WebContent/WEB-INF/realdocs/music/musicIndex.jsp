<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<title>Home page</title>

</head>
<body>
	<h1>Music</h1>
	<jsp:include page="./include/searchPanel.jsp"></jsp:include>
	<c:if
		test="${song.qualities.audio128 != null || song.qualities.audio128 == ''}">
		<video autoplay="autoplay" controls="controls">
			<source src="${song.qualities.audio128}" type="audio/mpeg">
		</video>
	</c:if>

</body>
</html>