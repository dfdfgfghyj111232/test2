package com.wang.servlet;

import com.wang.pojo.User;
import com.wang.utils.SendMailToAll;
import com.wang.utils.Sendmail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String email=req.getParameter("email");
        String emails=req.getParameter("emails");
        String table=req.getParameter("table");
        String message=req.getParameter("message");



        User user=new User(username,password,email,emails,table,message);
        //用户注册成功之后，给用户发送一封邮件
        //我们使用线程来专门发送邮件，防止出现耗时，和网站注册人数较多的情况
        Sendmail send=new Sendmail(user);
       // SendMailToAll sends=new SendMailToAll(user);
       // sends.sendMail(emails,message,table);
        send.start();
        //视图跳转
        req.setAttribute("message","注册成功！我们已经向您的邮箱发送了邮件，请您及时进行查收。由于网络原因，您收到邮件的时间存在延迟，敬请谅解~");
        req.getRequestDispatcher("info.jsp").forward(req,resp);
        System.out.println("success");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}


