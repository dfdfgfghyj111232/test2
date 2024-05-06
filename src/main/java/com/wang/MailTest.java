package com.wang;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

//发送一封简单的邮件
public class MailTest {
    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("mail.host", "smtp.qq.com");  //设置QQ邮件服务器
        prop.setProperty("mail.transport.protocol", "smtp"); // 邮件发送协议
        prop.setProperty("mail.smtp.auth", "true"); // 需要验证用户名密码

        // 关于QQ邮箱，还要设置SSL加密，加上以下代码即可
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        //使用JavaMail发送邮件的5个步骤

        //1、创建定义整个应用程序所需的环境信息的 Session 对象
        //使用QQ邮箱的时候才需要，其他邮箱不需要这一段代码
        Session session = Session.getDefaultInstance(prop, new Authenticator() {//获取和SMTP服务器的连接对象
            public PasswordAuthentication getPasswordAuthentication() {
                //发件人邮件用户名、授权码
                return new PasswordAuthentication("2582384538@qq.com", "gazzqaigehmddhgh");
            }
        });

        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);

        //2、通过session得到transport对象
        Transport ts = session.getTransport();//通过这一次和SMTP服务器的连接对象获取发送邮件的传输对象

        //3、使用邮箱的用户名和授权码连上SMTP邮件服务器，即登陆
        ts.connect("smtp.qq.com", "2582384538@qq.com", "gazzqaigehmddhgh");

        //4、创建邮件对象MimeMessage——点击网页上的写信
        //创建一个邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人——在网页上填写发件人
        message.setFrom(new InternetAddress("2582384538@qq.com"));//设置发件人
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发——在网页上填写收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("2582384538@qq.com"));//设置收件人
        //邮件的标题——在网页上填写邮件标题
        message.setSubject("简单邮件发送实现");//设置邮件主题
        /*
        //邮件的文本内容——在网页上填写邮件内容
        message.setContent("<h2 style='color:blue'>你好啊！</h2>", "text/html;charset=UTF-8");//设置邮件的具体内容
         */
        /*
          发送包含图片的复杂邮件
         */
        // 准备邮件数据
        // 准备图片数据
        MimeBodyPart image = new MimeBodyPart();//获取一个图片的MimeBodyPart对象
        //由于图片需要字符化才能传输，所以需要获取一个DataHandler对象
        DataHandler dh = new DataHandler(new FileDataSource("D:\\QQ文件\\SDA作业word\\File-Transfer-and-the-message-is-sent-master\\Java邮件发送\\src\\main\\resources\\images\\1.png"));
        //在我们的Body中放入这个处理的图片数据
        image.setDataHandler(dh);
        image.setContentID("bz.jpg");//为图片的MimeBodyPart对象设置一个ID，我们在文字中就可以使用它了

        // 准备正文数据
        MimeBodyPart text = new MimeBodyPart();//获取一个文本的MimeBodyPart对象
        //设置文本内容，注意在里面嵌入了<img src='cid:bz.jpg'>
        text.setContent("这是一封邮件正文带图片<img src='cid:bz.jpg'>的邮件", "text/html;charset=UTF-8");

        //=================================准备附件数据
        MimeBodyPart body1= new MimeBodyPart();
        body1.setDataHandler(new DataHandler(new FileDataSource("D:\\QQ文件\\SDA作业word\\ttt.txt")));
        body1.setFileName("机器人的运动范围.txt");

        // 描述数据关系
        MimeMultipart mm = new MimeMultipart();//获取MimeMultipart
        mm.addBodyPart(body1);
        mm.addBodyPart(text);//将文本MimeBodyPart对象加入MimeMultipart中
        mm.addBodyPart(image);//将图片MimeBodyPart对象加入MimeMultipart中
        mm.setSubType("mixed");

        //设置到消息中，保存修改
        message.setContent(mm);//将MimeMultipart放入消息对象中
        message.saveChanges();//保存上面的修改

        //5、发送邮件——在网页上点击发送按钮
        ts.sendMessage(message, message.getAllRecipients());


        //6、关闭连接对象，即关闭服务器上的连接资源
        ts.close();
    }
}
