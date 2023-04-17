<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Admin</title>
    <link rel="stylesheet" href="<c:url value="/static/layui/css/layui.css"/>">
</head>
<body>
<%--没有数据就请求数据--%>
<%--empty是除了==null之外还包括内部没东西（size==0）--%>
<c:if test="${requestScope.whitelist==null}">
    <%
        response.sendRedirect(request.getContextPath() + "/page/white/2214086D382FE0823550CAD6B19EA205?flag=getAllList");
    %>
</c:if>
<c:url value="/page/white/2214086D382FE0823550CAD6B19EA205" var="path"/>
<%--提示--%>
<c:if test="${not empty requestScope.result}" var="resultNoEmp">
    <script>
        window.onload = () => {
            layer.msg("${requestScope.result}", {time: 700});
        }
    </script>
</c:if>
<%--页面--%>
<div id="admin" style="padding: 50px 0 0 50px">
    <c:if test="${not empty applicationScope.apiTimes}">
        <h1>接口剩余访问次数：${applicationScope.apiTimes}</h1>
    </c:if>
    <a href="${pageScope.path}?flag=getAllList">
        <button type="button" class="layui-btn layui-btn-primary">刷新列表</button>
    </a>
    <br>
    <h1>白名单ip表</h1>
    <br>
    <table border="1" width="60%" style="text-align: center">
        <tr>
            <th>Id</th>
            <th>IP</th>
            <th>归属地</th>
            <th>备注</th>
            <th>创建时间</th>
        </tr>
        <c:if test="${not empty requestScope.whitelist}" var="noEmp">
            <c:forEach items="${requestScope.whitelist}" var="whiteIP">
                <tr>
                    <td>${whiteIP.id}</td>
                    <td>${whiteIP.ip_address}</td>
                    <td>${whiteIP.city}</td>
                    <td>${whiteIP.description}</td>
                    <td>${whiteIP.created_at}</td>
                    <td>
                        <button type="button" onclick="deleteIP('${whiteIP.id}')" class="layui-btn layui-btn-danger">
                            删除
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
    <br>
    <button type="button" onclick="addIP()" class="layui-btn">添加IP</button>
    <hr>
    <h1>标识表</h1>
    <br>
    <table border="1" width="60%" style="text-align: center">
        <tr>
            <th>key</th>
            <th>value</th>
            <th>描述</th>
        </tr>
        <c:if test="${not empty requestScope.identifierList}">
            <c:forEach items="${requestScope.identifierList}" var="identifier">
                <tr>
                    <td>${identifier.key}</td>
                    <td>${identifier.value}</td>
                    <td>${identifier.description}</td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
    <hr>
    <h1>错误表</h1>
    <br>
    <table border="1" width="90%" style="text-align: center">
        <tr>
            <th>id</th>
            <th>报错类型</th>
            <th>错误信息</th>
            <th>错误发生时间</th>
            <th>错误堆栈信息</th>
            <th>
                <a href="${pageScope.path}?flag=deleteAllErrLog">
                    <button type="button" class="layui-btn layui-btn-danger">
                        删除全部
                    </button>
                </a>
            </th>
        </tr>
        <c:if test="${not empty requestScope.errorList}">
            <c:forEach items="${requestScope.errorList}" var="error">
                <tr>
                    <td>${error.id}</td>
                    <td>${error.error_type}</td>
                    <td>${error.error_message}</td>
                    <td>${error.error_time}</td>
                    <td>${error.error_stack}</td>
                    <td>
                        <button type="button" onclick="deleteErrLog('${error.id}')" class="layui-btn layui-btn-danger">
                            删除
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
    <br>
    <a href="${pageScope.path}?flag=refresh">
        <button type="button" class="layui-btn layui-btn-warm">手动重新加载数据库到配置</button>
    </a>
    <br>
</div>
<script src="<c:url value="/static/layui/layui.js"/>"></script>
<script>
    layui.use(() => {
        const layer = layui.layer
            , form = layui.form;
    });
    const addIP = () => {
        layer.open({
            type: 1,
            area: ['420px', ''], //宽高
            offset: '100px',
            title: "请输入添加的IP",
            content: `
                <form style="padding: 20px;">
                    <label>
                        IP <input type="text" name="ip" maxlength="15" class="layui-input">
                    </label>
                    <br>
                    <label>
                        备注 <input type="text" name="description" maxlength="200" class="layui-input">
                    </label>
                    <br>
                    <div style="text-align: right">
                        <button type="button" onclick="checkIP(ip.value,description.value)"
                            class="layui-btn  layui-btn-primary layui-border-blue">
                                添加
                        </button>
                        <button type="reset"
                            class="layui-btn  layui-btn-primary layui-border-blue">
                                重置
                        </button>
                    </div>
                </form>`
        });
    }
    const checkIP = (ip, description) => {
        const ipRegex = /^(?:(?:1\d{2}|2[0-4]\d|25[0-5]|\d\d?)\.){3}(?:1\d{2}|2[0-4]\d|25[0-5]|\d\d?)$/;
        if (ipRegex.test(ip)) {
            if (description != null && description !== "" && description !== undefined) {
                window.location.replace("${pageScope.path}?flag=addIP&ip=" + ip + "&description=" + description);
            } else {
                layer.msg('备注不能为空', {icon: 2});
            }
        } else {
            layer.msg('IP地址格式错误', {icon: 2});
        }
    }
    const deleteIP = (id) => {
        layer.confirm('确定要删除吗？', {
            btn: ['确认', '取消'], //按钮
            offset: '100px'
        }, function () {
            window.location.replace('${pageScope.path}?flag=deleteIP&id=' + id);
        }, function () {
            layer.msg('正确的选择', {icon: 6, time: 1});
        });
    }
    const deleteErrLog = (id) => {
        layer.confirm('确定要删除吗？', {
            btn: ['确认', '取消'], //按钮
            offset: '100px'
        }, function () {
            window.location.replace('${pageScope.path}?flag=deleteErrLog&id=' + id);
        });
    }
</script>
</body>
</html>