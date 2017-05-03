<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  <meta charset="UTF-8">
  <%-- Bootstrap in maven lib --%>
  <link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.6/css/bootstrap.min.css">

  <%-- Bootstrap in contents download --%>
  <%-- <link rel="stylesheet" type="text/css" href="contents/bootstrap/css/bootstrap.min.css"> --%>

<title>Music</title>
</head>
<body>
	<h1>Music</h1>
	<h2>${pageContext.request.contextPath}</h2>
	<jsp:include page="./include/searchPanel.jsp"></jsp:include>
	<c:if
		test="${song.qualities.audio128 != null || song.qualities.audio128 == ''}">
		<video autoplay="autoplay" controls="controls">
			<source src="${song.qualities.audio128}" type="audio/mpeg">
		</video>
	</c:if>

<script type="text/javascript" src="webjars/jquery/2.1.4/jquery.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>