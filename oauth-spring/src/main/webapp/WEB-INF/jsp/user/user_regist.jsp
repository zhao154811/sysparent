<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>用户注册</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

</head>

<body>
<form action="<%=basePath %>user/regist/userRegist" method="post">
    <table align="center">
        <tr>
            <td colspan="2">用户注册</td>
        </tr>
        <tr>
            <td>用户名:</td>
            <td><input type="text" name="userName"/></td>
        </tr>
        <tr>
            <td>密码:</td>
            <td><input type="password" name="password"/>
                <input type="hidden" name="client_id" value="${client_id}"/>
                <input type="hidden" name="ckCode" value="${ckCode}"/>
            </td>
        </tr>
        <!--  <tr>
    <td>用户角色:</td>
   <td>
   <c:forEach items= "${roles}" var= "cust" varStatus= "status" > 
    <form:checkbox  path="user.roles" value="${cust._id}" label="${cust.roleName}"/>
     </c:forEach>
   
   
   </td>
   </tr> -->
        <tr>
            <td><input type="submit" value="提交"></td>
        </tr>
    </table>
</body>
</form>
</html>
