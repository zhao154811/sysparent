<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../taglib.jsp" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>用户资料更新</title>

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
<table>
    <tr>
        <td>用户名</td>
        <td>
            ${userInfo.userName}
            <input type="hidden" name="_id" value="${userInfo._id }"/>
        </td>
    </tr>
    <tr>
        <td>真实姓名</td>
        <td>
            <input type="text" name="realName" value="${userInfo.realName}"/>
        </td>
    </tr>
    <tr>
        <td>E-Mail</td>
        <td><input type="text" name="email" value="${userInfo.email}"/></td>
    </tr>
    <tr>
        <td>地址</td>
        <td><input type="text" name="address" value="${userInfo.address}"/></td>
    </tr>
    <tr>
        <td>证件号码</td>
        <td><input type="text" name="idNo" value="${userInfo.idNo}"/></td>
    </tr>
    <tr>
        <td>电话</td>
        <td><input type="text" name="phoneNum" value="${userInfo.phoneNum}"/></td>
    </tr>
</table>
</body>
</html>
