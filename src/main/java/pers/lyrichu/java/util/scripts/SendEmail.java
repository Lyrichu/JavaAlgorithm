package pers.lyrichu.java.util.scripts;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.security.GeneralSecurityException;
import java.util.Properties;

import com.sun.mail.util.MailSSLSocketFactory;


public class SendEmail {
    public static void main(String[] args) throws GeneralSecurityException {
        String from = "lyrichu@foxmail.com";
        String[] toList = {"919987476@qq.com"};
        String fromPasswd = "bzphfvgjsgwvbbbj"; // 授权码替代原始密码
        String host = "smtp.qq.com";
        String subject = "java test mail";
        String message = "<div>Hello!</div><br/><hr/><div>Wish you happy every day!</div>";
        String[] attachments = {"src/main/resources/beauty.jpeg","/home/ebooks/Effective Java 中文第二版-frompage1-topage10.pdf"};
        postEmail(from,toList,fromPasswd,host,subject,message,attachments);
        System.out.printf("Send email from %s to %s successfully!\n",from,String.join(",",toList));
    }

    // 发送一个普通的邮件
    public static void postEmail(final String from,String[] toList,final String fromPasswd, String host,
                                  String subject, String message) throws GeneralSecurityException {
        Properties props = System.getProperties();
        // 设置邮件服务器
        props.setProperty("mail.smtp.host",host);
        props.put("mail.smtp.auth","true");
        // 避免文件名过长被截断
        props.setProperty("mail.mime.splitlongparameters", "false");
        MailSSLSocketFactory msf = new MailSSLSocketFactory();
        msf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable","true");
        props.put("mail.smtp.ssl.socketFactory",msf);
        // 获取默认的session对象
        Session session = Session.getDefaultInstance(
                props, new Authenticator() {
                    // 发件人用户名和密码
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,fromPasswd);
                    }
                }
        );
        try {
            // 创建默认的mimeMessage对象
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(from);
            //set to head
            for (int i =0;i<toList.length;i++) {
                String to = toList[i];
                msg.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            }
            // set subject
            msg.setSubject(subject);
            // 这种方式是支持html格式内容的
            msg.setContent(message,"text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(msg);
        } catch (MessagingException e) {
            System.err.println("error:"+e);
        }
    }

    // 发送带有附件的邮件
    public static void postEmail(final String from,String[] toList,final String fromPasswd,String host,
                                                String subject,String message,String[] files) throws GeneralSecurityException {
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host",host);
        props.put("mail.smtp.auth","true");
        // 避免文件名过长被截断
        props.setProperty("mail.mime.splitlongparameters", "false");
        MailSSLSocketFactory msf = new MailSSLSocketFactory();
        msf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable","true");
        props.put("mail.smtp.ssl.socketFactory",msf);
        Session session = Session.getDefaultInstance(
                props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,fromPasswd);
                    }
                }
        );
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(from);
            // 设置收件人
            for (int i = 0;i<toList.length;i++) {
                String to = toList[i];
                msg.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            }
            msg.setSubject(subject);
            // 添加邮件的各个部分,包括文本和附件
            Multipart multipart = new MimeMultipart();
            // 添加邮件正文
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(message,"text/html;charset=UTF-8");
            multipart.addBodyPart(contentPart);
            // 通过遍历的方式添加附件
            if (files != null && files.length > 0) {
                for (String file:files) {
                    BodyPart attachmentBodyPart = new MimeBodyPart();
                    File f = new File(file);
                    if (!f.exists()) {
                        System.err.printf("Error:%s doesn't exist!Skip it!\n",file);
                        continue;
                    }
                    DataSource dataSource = new FileDataSource(file);
                    attachmentBodyPart.setDataHandler(new DataHandler(dataSource));
                    attachmentBodyPart.setFileName(MimeUtility.encodeText(f.getName()));
                    // 添加附件
                    multipart.addBodyPart(attachmentBodyPart);
                }
            }
            // 将多媒体对象放到message中
            msg.setContent(multipart);
            // 保存邮件
            msg.saveChanges();
            // smtp验证
            Transport transport = session.getTransport("smtp");
            transport.connect(host,from,fromPasswd);

            // 发送邮件
            transport.sendMessage(msg,msg.getAllRecipients());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
