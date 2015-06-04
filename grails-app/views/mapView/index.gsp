<%--
  Created by IntelliJ IDEA.
  User: sbortman
  Date: 6/3/15
  Time: 3:29 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Map View</title>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="mapView.css"/>
</head>

<body>
<div class="nav">
    <ul>
        <li><g:link uri="/" class="home">Home</g:link></li>
    </ul>
</div>
<div class="content">
    <h1>Map View</h1>
    <div id="map"></div>
</div>
<asset:javascript src="mapView.js"/>
<g:javascript>
$(document).ready(function(){
    MapWidget.init();
});
</g:javascript>

</body>
</html>