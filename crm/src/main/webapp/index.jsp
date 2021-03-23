<%--
  Created by IntelliJ IDEA.
  User: 10729
  Date: 2021/1/25
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() +  "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="js/jquery-3.4.1.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn").click(function () {
                alert("单机成功");
                $.ajax({
                    url:"some.do",
                    type:"get",
                    dataType:"json",
                    success:function (data) {
                        alert("ajax成功获取数据");
                        $("#s1").html(data.s1.id);
                        $("#name1").html(data.s1.name);
                        $("#age1").html(data.s1.age);
                        $("#s2").html(data.s2.id);
                        $("#name2").html(data.s2.name);
                        $("#age2").html(data.s2.age);
                    }
                })
            })
        })
    </script>
    <base href="<%=basePath%>"/>
</head>
<body>
    <center>
        <button id="btn">点击</button><br>
        编号：<span id="s1"></span>
        姓名：<span id="name1"></span>
        年龄：<span id="age1"></span><br>
        编号：<span id="s2"></span>
        姓名：<span id="name2"></span>
        年龄：<span id="age2"></span>
    </center>
</body>
</html>
