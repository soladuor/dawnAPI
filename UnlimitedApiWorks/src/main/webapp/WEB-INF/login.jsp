<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/page/55AF7DD54A9DA8280A2EBD20659B9734?flag=login" method="post">
    <label>
        登录名：
        <input type="text" name="name">
    </label>
    <label>
        密码：
        <input type="password" name="password">
    </label>
    <%--验证码--%>
    <label>
        验证码：
        <input type="text" name="code">
        <img src="${pageContext.request.contextPath}/page/code/A4F1C01849C3FE3179AD265145B34BF1" alt="">
    </label>
    <%--这个按钮点击是无法登录的（笑--%>
    <button type="button">登录</button>
</form>
</body>
</html>
