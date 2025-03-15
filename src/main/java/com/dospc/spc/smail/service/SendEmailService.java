package com.dospc.spc.smail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    //    @Autowired
//    private JavaMailSender emailSender;
    private final JavaMailSender yandexMailSender;
    private final JavaMailSender zohoMailSender;

    @Autowired
    public SendEmailService(
            @Qualifier("yandexMailSender") JavaMailSender yandexMailSender,
            @Qualifier("zohoMailSender") JavaMailSender zohoMailSender) {
        this.yandexMailSender = yandexMailSender;
        this.zohoMailSender = zohoMailSender;
    }

    public void sendEmailUsingYandex(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("do.lh@yandex.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        yandexMailSender.send(message);
    }

    public void sendEmailUsingZoho(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dolh.dxm@zohomail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        zohoMailSender.send(message);
    }
//    public void sendEmail(String to, String subject, String text) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            System.out.println("text : " + text);
//            message.setFrom("dolh.dxm@zohomail.com");
//            message.setTo(to);
//            message.setSubject(subject);
//            message.setText(text);
//            emailSender.send(message);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//    }
}
