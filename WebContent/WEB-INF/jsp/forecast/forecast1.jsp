<html>
<head>
<title>Weather One Results</title>
<style type="text/css">
</style>
</head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<body>
		<div style="font-family: verdana; padding: 10px; border-radius: 10px; font-size: 12px; text-align:center;">
		${message}
		</div>
	<br>
	<div style="font-family: verdana; padding: 10px; border-radius: 10px; font-size: 12px; text-align:center;">
		Temperature: value="${forecast.temperature}"<br/>
		Summary: value="${forecast.summary}"<br/>
	</div>
</body>
</html>