package com.dospc.spc.smail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${dxm.listmailSender}")
    private String senderEmails;

    @Value("${dxm.listmailSenderNames}")
    private String senderNames;
//    @Bean
//    public JavaMailSender sendGridMailSender() {
//        return createMailSender("smtp.sendgrid.net", 587, "apikey", "your-sendgrid-api-key");
//    }
//
//    @Bean
//    public JavaMailSender mailgunMailSender() {
//        return createMailSender("smtp.mailgun.org", 587, "your-mailgun-username", "your-mailgun-password");
//    }
//
//    @Bean
//    public JavaMailSender sesMailSender() {
//        return createMailSender("email-smtp.your-region.amazonaws.com", 587, "your-ses-smtp-username", "your-ses-smtp-password");
//    }

    @Bean("yandexMailSender")
    public JavaMailSender yandexMailSender() {
        return createMailSender("smtp.yandex.com", 587, "do.lh@yandex.com", "axbiraozfaqhwbpu");
    }

    @Bean("zohoMailSender")
    public JavaMailSender zohoMailSender() {
        return createMailSender("smtp.zoho.com", 587, "dolh.dxm@zohomail.com", "lehuudo11");
    }

    private JavaMailSender createMailSender(String host, int port, String username, String password) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public List<Integer> mailLimits() {
        return Arrays.asList(500, 200); // Giới hạn gửi của từng dịch vụ (Yandex: 500, Zoho: 200)
    }


    @Bean
    public List<String> senderEmails() {
        return Arrays.asList(senderEmails.split(",")); // Cắt chuỗi bằng dấu phẩy và chuyển thành List
    }

    @Bean
    public List<String> senderNames() {
        return Arrays.asList(senderNames.split(",")); // Cắt chuỗi bằng dấu phẩy và chuyển thành List
    }
}