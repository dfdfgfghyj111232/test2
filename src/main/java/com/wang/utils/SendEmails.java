package com.wang.utils;
import com.sun.mail.util.MailSSLSocketFactory;
import com.wang.pojo.User;


import javax.mail.*;
import javax.mail.internet.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class SendEmails {
    private User user;
    /**
     * @param sendMailAccount    发送邮件
     * @param sendMailPassword   发送邮件密码
     * @param receiveMailAccount 收件邮件
     * @param subject            标题
     * @param content            内容
     */
    public static void sendMails(final String sendMailAccount, final String sendMailPassword, String receiveMailAccount, String subject, String content) {


        Properties prop = new Properties();
        //协议
        prop.setProperty("mail.transport.protocol", "smtp");
        //服务器
        prop.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
        //端口
        prop.setProperty("mail.smtp.port", "587");
        //使用smtp身份验证
        prop.setProperty("mail.smtp.auth", "true");
        //企业邮箱必须要SSL
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
        } catch (GeneralSecurityException e1) {
            e1.printStackTrace();
        }
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        //获取Session对象
        Session s = Session.getDefaultInstance(prop, new Authenticator() {
            //此访求返回用户和密码的对象
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                PasswordAuthentication pa = new PasswordAuthentication(sendMailAccount, sendMailPassword);
                return pa;
            }
        });
        //设置session的调试模式，发布时取消
        s.setDebug(true);
        MimeMessage mimeMessage = new MimeMessage(s);
        try {
            // 发信邮箱
            mimeMessage.setFrom(new InternetAddress(sendMailAccount));
            // 收信邮箱
            // 单个发送
            //mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiveMailAccount));
            // 群发
            mimeMessage.addRecipients(Message.RecipientType.TO, addressMails(receiveMailAccount));
            //mimeMessage.addRecipients(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            //设置主题

            mimeMessage.setSubject(subject);//邮件标题
            //mimeMessage.setSubject("yyyyrrrr", "UTF-8");

            mimeMessage.setSentDate(new Date());
            //设置内容
            //mimeMessage.setText("内容");//邮件内容
            //设置内容
            /* 创建用于组合邮件正文和附件的MimeMultipart对象 */
            MimeMultipart multipart = new MimeMultipart();
            // 设置HTML内容
            MimeBodyPart content1 = createContent(content);
            multipart.addBodyPart(content1);
            // 将组合的MimeMultipart对象设置为整个邮件的内容，要注意调用saveChanges方法进行更新
            mimeMessage.setContent(multipart,"text/html;charset=UTF-8");
            mimeMessage.saveChanges();
            //发送
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static InternetAddress[] addressMails(String receiveMails) {
        //多个接收账号
        //        String str = "xxx@xxx.com,xxx@xxx.com";
        InternetAddress[] address = null;
        try {
            List list = new ArrayList();//不能使用string类型的类型，这样只能发送一个收件人
            String[] median = receiveMails.split(",");//对输入的多个邮件进行逗号分割
            for (int i = 0; i < median.length; i++) {
                list.add(new InternetAddress(median[i]));
            }
            address = (InternetAddress[]) list.toArray(new InternetAddress[list.size()]);
        } catch (AddressException e) {
            e.printStackTrace();
        }
        return address;
    }

    /**
     * 创建HTML格式的邮件内容
     *
     * @param body 邮件内容
     * @return 邮件内容实体
     * @throws Exception
     */
    public static MimeBodyPart createContent(String body) throws Exception {

        /* 创建代表组合MIME消息的MimeMultipart对象和该对象保存到的MimeBodyPart对象 */
        MimeBodyPart content = new MimeBodyPart();

        // 创建一个MImeMultipart对象
        MimeMultipart multipart = new MimeMultipart();

        // 创建一个表示HTML正文的MimeBodyPart对象，并将它加入到前面的创建的MimeMultipart对象中
        MimeBodyPart htmlBodyPart = new MimeBodyPart();
        htmlBodyPart.setContent(body, "text/html;charset=UTF-8");
        multipart.addBodyPart(htmlBodyPart);

        // 将MimeMultipart对象保存到MimeBodyPart对象中
        content.setContent(multipart);

        return content;
    }
}

   // @Test
    //public void Test(){
      //  sendMail("发送邮箱@midland.com.cn","密码","接收1@midland.com.cn,接收2@midland.com.cn","邮件标题","内容");
   // }

