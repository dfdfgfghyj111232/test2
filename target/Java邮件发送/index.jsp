<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Hello World!</h2>
<form action="${pageContext.request.contextPath}/RegisterServlet.do" method="post">

    <%--
    发件人：<input type="text" name="username">
    收件人：<input type="text" name="email">
    内容<input type="text" name="pwd">

    <input type="submit" value="注册">
     <div id="login-control">
        <input id="btn-login" type="submit" value="登录"/>
    </div>
--%>


    <table>
        <tr>
            <td class="label-input">发件人</td>
            <td><label>
                <input type="text" name="username" class="text-field" maxlength="20"/>
            </label></td>
        </tr>

        <tr>
            <td class="label-input">发件码</td>
            <td><label>
                <input type="text" name="password" class="text-field" maxlength="20"/>
            </label></td>
        </tr>

        <tr>
            <td class="label-input">收件人</td>
            <td><label>
                <input type="text" name="email" class="text-field" maxlength="20"/>
            </label></td>
        </tr>



        <tr>
            <td class="label-input">群发收件人</td>
            <td>
                <textarea name="emails" rows="4" cols="50" maxlength="200"></textarea>
            </td>
        </tr>


        <tr>
            <td class="label-input">标题</td>
            <td><label>
                <input type="text" name="table"  maxlength="20"/>
            </label></td>
        </tr>

        <tr>
            <td class="label-input">内容</td>
            <td>
                <textarea name="message" rows="4" cols="50" maxlength="200"></textarea>
            </td>
        </tr>
    </table>
        <input type="submit" value="发送">




</form>
</body>
</html>


