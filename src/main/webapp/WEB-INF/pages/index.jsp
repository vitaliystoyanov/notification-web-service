<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.min.js"></script>
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true"></script>
    <spring:url value="/resources/js/gmaps.js" var="gmaps"/>
    <spring:url value="/resources/js/main.js" var="main"/>
    <spring:url value="/resources/css/main.css" var="mainCSS"/>

    <script src="${gmaps}"></script>
    <script src="${main}"></script>
    <link href="${mainCSS}" rel="stylesheet">

</head>
<body>
<div id="header"></div>
<div id="map" class="map-large"></div>
</body>
</html>