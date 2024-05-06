package com.wang.utils;
import com.wang.pojo.User;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SendMailToAll {
    private static User user;
    static final String from =user.getUsername();
    static final String pass =user.getPassword();
    private static final String USER = from; // 发件人称号，同邮箱地址
    private static final String PASSWORD = pass; // 如果是qq邮箱可以使户端授权码，或者登录密码
    public SendMailToAll(User user){
        this.user=user;
    }
    /**
     *
    // * @param to 收件人邮箱
    // * @param text 邮件正文
    // * @param title 标题
     */
    /* 发送验证信息的邮件 */
    public static boolean sendMail(String emails, String messages, String table, final String from, final String pass){
        try {
            final Properties props = new Properties();
            // 是否需要用户认证
            props.put("mail.smtp.auth", "true");
            // SMTP主机名
            props.put("mail.smtp.host", "smtp.qq.com");
            // 主机端口号
            props.put("mail.smtp.port", "587");
            // 启用TLS加密
            props.put("mail.smtp.starttls.enable", "false");

            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, pass);
                  //  return new PasswordAuthentication("2582384538@qq.com","gazzqaigehmddhgh");
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            //设置为debug模式，可以查看详细的发送log
            mailSession.setDebug(true);

            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);

           // String from = user.getUsername();
            Address form = new InternetAddress(from);

            message.setFrom(form);
           // message.setFrom(new InternetAddress("2582384538@qq.com"));

           // String emails =user.getEmails();
            String[] emailArray = emails.split("/");  // 使用斜杠分隔字符串
            InternetAddress[]tos = new InternetAddress[emailArray.length];
            for (int i = 0; i < emailArray.length; i++) {
                tos[i] = new InternetAddress(emailArray[i]);
            }

            //发件人
           // final String from =user.getUsername();
           // message.setFrom(new InternetAddress(from));

            message.setRecipients(Message.RecipientType.TO, tos);
          //  message.setRecipients(Message.RecipientType.TO, "2582384538@qq.com");
            // 邮件主题
            message.setSubject(table, "UTF-8");
            //向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            MimeMultipart multipart = new MimeMultipart();
            //设置邮件的文本内容
            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setContent( messages, "text/html;charset=UTF-8");
            multipart.addBodyPart(contentPart);

            //将multipart对象放到message中
            message.setContent(multipart);
            //设置显示的发件时间
            message.setSentDate(new Date());
            //保存前面的设置
            message.saveChanges();
            //发送邮件到所有的收件地址
            Transport.send(message, message.getAllRecipients());
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }




    }

