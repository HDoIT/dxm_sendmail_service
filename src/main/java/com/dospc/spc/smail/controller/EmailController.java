package com.dospc.spc.smail.controller;

import com.dospc.spc.smail.request.EmailRequest;
import com.dospc.spc.smail.service.BulkEmailService;
import com.dospc.spc.smail.service.MultiSmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmailController {

    @Autowired
    private BulkEmailService bulkEmailService;
    @Autowired
    private MultiSmtpEmailService emailService;

    @GetMapping("/send-emails")
    public String sendEmails(@RequestParam String apiKey,@RequestParam String spreadsheetId, @RequestParam String range,@RequestParam String subject, @RequestParam String content) {
        try {
            bulkEmailService.sendBulkEmails(apiKey,spreadsheetId, range,subject,content);
            return "Emails are being sent in the background.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error sending emails: " + e.getMessage();
        }
    }


    @PostMapping("/send")
    public String sendEmaild(@RequestParam String apiKey,@RequestParam String spreadsheetId, @RequestParam String range,@RequestParam String subject, @RequestParam String content) {
        try{
            bulkEmailService.sendBulkEmailsV2(apiKey,spreadsheetId, range,subject,content);

//        emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
            return "Email sent successfully!";
        }catch (Exception e){
            return "Error sending emails: " + e.getMessage();
        }
    }


//    @GetMapping("/send-byHotmail")
//    public String sendByHotmail(@RequestParam String email) {
//        try {
//            String subject = "Chào mừng bạn đến với dịch vụ của chúng tôi";
//            String text = "Cảm ơn bạn đã đăng ký sử dụng dịch vụ của chúng tôi!";
//
//            bulkEmailService.sendBulkByHotMails(email, subject, text);
//
//            return "Hotmail are being sent in the background.";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error sending emails: " + e.getMessage();
//        }
//    }
}