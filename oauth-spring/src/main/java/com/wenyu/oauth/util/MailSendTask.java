package com.wenyu.oauth.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Created by Zhaowy on 2014/7/24.
 */
public class MailSendTask implements Runnable {

    protected final Log logger = LogFactory.getLog(getClass());
    //接收人
    private String[] toAddresses;
    //抄送
    private String[] cc;

    @Autowired
    private JavaMailSender mailSender;


    public void send() {
        //获取JavaMailSender bean
//		JavaMailSender sender = (JavaMailSender) ctx.getBean("mailSender");
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.enlink-mob.com");
        SimpleMailMessage mail = new SimpleMailMessage(); //<span style="color: #ff0000;">注意SimpleMailMessage只能用来发送text格式的邮件</span>

        try {
            mail.setTo("154811696@qq.com");//接受者
            mail.setFrom("zhaowy@enlink-mob.com");//发送者,这里还可以另起Email别名，不用和xml里的username一致
            mail.setSubject("spring mail test!");//主题
            mail.setText("springMail的简单发送测试");//邮件内容
            sender.send(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

    }
}