package com.wang.utils;

import com.wang.pojo.User;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Sendmail extends Thread {
    //用于给用户发送邮件的邮箱
    private User user;
   // private String from="2582384538@qq.com";
  // private String from =user.getUsername();
    //发送邮件的服务器地址
    private String host="smtp.qq.com";

  //  private User user;
 //  private String from=user.getUsername();
  // private String host="smtp.qq.com";

    public Sendmail(User user){
        this.user=user;
    }

   // private String from =user.getUsername();
    //private String from = (user != null) ? user.getUsername() : "defaultUsername";
    @Override
    public void run() {
        try {


            Properties prop=new Properties();
            prop.setProperty("mail.host",host);///设置QQ邮件服务器
            prop.setProperty("mail.transport.protocol","smtp");///邮件发送协议
            prop.setProperty("mail.smtp.auth","true");//需要验证用户密码
            //QQ邮箱需要设置SSL加密
            MailSSLSocketFactory sf=new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable","true");
            prop.put("mail.smtp.ssl.socketFactory",sf);

            //使用javaMail发送邮件的5个步骤
            //1.创建定义整个应用程序所需要的环境信息的session对象
            final String from =user.getUsername();
            final String pass =user.getPassword();
            Session session= Session.getDefaultInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {

                    //return new PasswordAuthentication(from,"gazzqaigehmddhgh");
                    return new PasswordAuthentication(from,pass);
                }
            });
            //开启session的debug模式，这样可以查看到程序发送Email的运行状态
            session.setDebug(true);
            //2.通过session得到transport对象
            Transport ts=session.getTransport();
            //3.使用邮箱的用户名和授权码连上邮件服务器

            String pass2 =user.getPassword();
             ts.connect(host,from,pass);


            //4.创建邮件：写文件
            //注意需要传递session
            MimeMessage message=new MimeMessage(session);
            //指明邮件的发件人
            message.setFrom(new InternetAddress(from));
            //指明邮件的收件人
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(user.getEmail()));
            //邮件标题
            final String table =user.gettable();
            //message.setSubject("注册通知");

            message.setSubject(table);

            final String messages =user.getmessage();
            message.setContent(messages ,"text/html;charset=UTF-8");

            //5.发送邮件
            ts.sendMessage(message,message.getAllRecipients());
            final String emails =user.getEmails();

            SendEmails.sendMails(from,pass,emails,table,messages);
            //6.关闭连接
            ts.close();
        }catch (Exception e){
            System.out.println(e);
        }

    }
}

