<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>员工管理</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script src="${pageContext.request.contextPath}/js/my.js?v=2"></script>
</head>

<body class="hold-transition skin-red sidebar-mini">
<div class="box-header with-border">
    <h3 class="box-title">员工管理</h3>
    <span id="msg" style="color: green; margin-left: 20px;"></span>
</div>
<div class="box-body">
    <!-- 添加员工 -->
    <div class="box-tools pull-left">
        <form id="addUserForm" class="form-inline">
            <div class="form-group">
                <label>姓名：</label>
                <input type="text" name="name" class="form-control" placeholder="请输入员工姓名">
            </div>
            <div class="form-group">
                <label>邮箱：</label>
                <input type="text" name="email" class="form-control" placeholder="请输入邮箱（登录账号）">
            </div>
            <div class="form-group">
                <label>密码：</label>
                <input type="password" name="password" class="form-control" placeholder="请输入密码">
            </div>
            <div class="form-group">
                <label>角色：</label>
                <select name="role" class="form-control">
                    <option value="user">普通用户</option>
                    <option value="admin">管理员</option>
                </select>
            </div>
            <button type="button" class="btn btn-primary" onclick="addUser()">添加员工</button>
            <button type="reset" class="btn btn-default">重置</button>
        </form>
    </div>
    <div style="clear:both;"></div>
    <br/>
    <!-- 员工列表 -->
    <div class="table-box">
        <table id="dataList" class="table table-bordered table-striped table-hover dataTable text-center">
            <thead>
            <tr>
                <th class="sorting_asc">员工ID</th>
                <th class="sorting">姓名</th>
                <th class="sorting">邮箱（账号）</th>
                <th class="sorting">角色</th>
                <th class="sorting">状态</th>
                <th class="text-center">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pageResult.rows}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td>
                        <c:choose>
                            <c:when test="${user.role == 'admin'}">
                                <span class="label label-primary">管理员</span>
                            </c:when>
                            <c:otherwise>
                                <span class="label label-info">普通用户</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${user.status == '0'}">
                                <span class="label label-success">正常</span>
                            </c:when>
                            <c:otherwise>
                                <span class="label label-danger">离职</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="text-center">
                        <c:if test="${user.status == '0' && user.role != 'admin'}">
                            <button type="button" class="btn bg-olive btn-xs" onclick="delUser(${user.id})">离职</button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!-- 分页插件 -->
        <div id="pagination" class="pagination"></div>
    </div>
</div>
</body>
<script>
    // 添加员工
    function addUser() {
        var url = getProjectPath() + "/user/addUser";
        $.post(url, $("#addUserForm").serialize(), function (response) {
            if (response.success == true) {
                $("#msg").text("添加员工成功!");
                setTimeout(function () {
                    window.location.href = getProjectPath() + "/user/search";
                }, 500);
            } else {
                alert(response.message);
            }
        });
    }
    // 办理离职
    function delUser(id) {
        var r = confirm("确定办理离职?");
        if (r) {
            var url = getProjectPath() + "/user/delUser?id=" + id;
            $.get(url, function (response) {
                alert(response.message);
                if (response.success == true) {
                    window.location.href = getProjectPath() + "/user/search";
                }
            });
        }
    }
    /*分页插件展示的总页数*/
    pageargs.total = Math.ceil(${pageResult.total}/pageargs.pagesize);
    /*分页插件当前的页码*/
    pageargs.cur = ${pageNum}
    /*分页插件页码变化时将跳转到的服务器端的路径*/
    pageargs.gourl = "${gourl}"
    /*分页效果*/
    pagination(pageargs);
</script>
</html>