<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<html ng-app>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="./bootstrap/css/bootstrap.min.css" media="screen">
<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet" href="./bootstrap/css/bootstrap-theme.min.css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="./js/jquery-2.1.4.min.js"></script>
<!-- angular。-->
<script src="./js/angular.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="./bootstrap/js/bootstrap.min.js"></script>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar"><span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span></button>
            <a class="navbar-brand" href="#">Project name</a></div>
        <div id="navbar" class="navbar-collapse collapse">
            <form class="navbar-form navbar-right" role="form">
                <div class="form-group"><input placeholder="Email" class="form-control"></div>
                <div class="form-group"><input type="password" placeholder="Password" class="form-control"></div>
                <button type="submit" class="btn btn-success">Sign in</button>
            </form>
        </div>
    </div>
</nav>
<div class="container" style="padding-top:50px">
    <div class="row">
        <div class="col-md-4"><h2>Heading</h2>
            <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris
                condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis
                euismod. Donec sed odio dui.</p>
            <p><a class="btn btn-default" href="#" role="button">View details »</a></p></div>
        <div class="col-md-4"><h2>Heading</h2>
            <p>Donec id elit non mi porta gravida at eget metus. Fusce dapibus, tellus ac cursus commodo, tortor mauris
                condimentum nibh, ut fermentum massa justo sit amet risus. Etiam porta sem malesuada magna mollis
                euismod. Donec sed odio dui.</p>
            <p><a class="btn btn-default" href="#" role="button">View details »</a></p></div>
        <div class="col-md-4"><h2>Heading</h2>
            <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula
                porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut
                fermentum massa justo sit amet risus.</p>
            <p><a class="btn btn-default" href="#" role="button">View details »</a></p></div>
    </div>
    <hr>
    <footer><p>© Company 2014</p></footer>
</div>
<h2>
    <a href="<%=basePath %>oauth/authorize?client_id=ucenter-sys-client&redirect_uri=http://localhost&response_type=code&scope=read">ucenter-sys-oauth</a>
</h2>

<h2>
    <a href="<%=basePath %>oauth/authorize?client_id=16f5a235-c664-3914-b3be-58bef97017a9&redirect_uri=http://localhost&response_type=code&scope=user_base">test2</a>
</h2>

<h2>
    <a href="<%=basePath %>oauth/token?client_id=16f5a235-c664-3914-b3be-58bef97017a9&client_secret=ce1c8082f234433d897f6a4704e26c2e&grant_type=authorization_code&code=${param.code }&redirect_uri=http://localhost"
       class="btn btn-default">ucenter-sys-token</a>
</h2>

<h2>
    <a href="<%=basePath %>oauth/token?client_id=ucenter-client&client_secret=411622fc06ccc29fde7693d9d8c20363&grant_type=authorization_code&code=${param.code }&redirect_uri=http://localhost"
       class="btn btn-lg btn-warning" role="button">ucenter-oauth</a>
</h2>

<h2>
    <a href="<%=basePath %>oauth/authorize?client_id=auto-china&redirect_uri=http://localhost&response_type=code&scope=read,write"
       class="btn btn-lg btn-danger" role="button">auto-china</a>
</h2>

<h2>
    <a href="<%=basePath %>oauth/token?client_id=auto-china&client_secret=411622fc06ccc29fde7693d9d8c20363&grant_type=authorization_code&code=${param.code }&redirect_uri=http://localhost"
       class="btn btn-lg btn-link" role="button">auto-china-token</a>
</h2>
</body>
</html>
