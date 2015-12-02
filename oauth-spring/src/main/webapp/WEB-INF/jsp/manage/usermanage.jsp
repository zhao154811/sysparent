<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="../taglib.jsp" %>
<script type="text/javascript">
    $(document).ready(function () {
        $("a[name='updatebtn']").mouseover(function () {
            var href = $(this).attr("href");
            $(this).attr("title", "sssss");

        });
    });
</script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>用户管理</title>

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
        <td>角色</td>
        <td>是否可用</td>
        <td>创建时间</td>
        <td>来源client</td>
        <td>操作</td>

    </tr>
    <c:forEach items="${userlist }" var="user" varStatus="s">
        <tr>
            <td>${user.userName }</td>

            <td><c:forEach items="${user.authorities }" var="roles"
                           varStatus="rolestatus">${roles.roleName }</c:forEach></td>

            <td><c:out value="${user.status }"/></td>
            <td><fmt:formatDate value="${user.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td><c:out value="${user.mongoclient.client_id }"/></td>
            <td><a name="updatebtn"
                   href="<%=basePath %>manage/user/manage/getUserInfoByName?userName=${user.userName }">修改用户资料</a><a
                    href="<%=basePath %>manage/user/delete">删除用户</a><a href="<%=basePath %>manage/user/lock">锁定用户</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
