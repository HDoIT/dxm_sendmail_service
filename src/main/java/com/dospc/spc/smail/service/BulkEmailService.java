package com.dospc.spc.smail.service;

import com.dospc.spc.smail.service.GoogleSheetService;
import com.dospc.spc.smail.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class BulkEmailService {

    @Autowired
    private SendEmailService emailService;

    @Autowired
    private MultiSmtpEmailService multiSmtpEmailService;

    @Autowired
    private GoogleSheetService googleSheetsService;

    public void sendBulkEmails(String apiKey,String spreadsheetId, String range,String Subject,String content) throws Exception {
        List<List<Object>> emails = googleSheetsService.readSheet(apiKey,spreadsheetId, range);

        ExecutorService executor = Executors.newFixedThreadPool(20);

        for (List<Object> row : emails) {
            String email = (String) row.get(0);
            System.out.println("email " + email);
            executor.submit(() -> {
                emailService.sendEmailUsingYandex(email, Subject, content);
            });
        }

        executor.shutdown();
    }

    public void sendBulkEmailsV2(String apiKey,String spreadsheetId, String range,String Subject,String content) throws Exception {
        List<List<Object>> emails = googleSheetsService.readSheet(apiKey,spreadsheetId, range);

        ExecutorService executor = Executors.newFixedThreadPool(20);

        for (List<Object> row : emails) {
            String email = (String) row.get(0);
            System.out.println("email " + email);
            executor.submit(() -> {
                multiSmtpEmailService.sendEmail(email, Subject, content);
            });
        }

        executor.shutdown();
    }

    public void sendBulkEmailsV3(String apiKey,String spreadsheetId, String range,String Subject,String content) throws Exception {
        List<List<Object>> emails = googleSheetsService.readSheet(apiKey,spreadsheetId, range);

        ExecutorService executor = Executors.newFixedThreadPool(20);

        for (List<Object> row : emails) {
            String email = (String) row.get(0);
            System.out.println("email " + email);
            executor.submit(() -> {
                emailService.sendEmailUsingZoho(email, "Subject", "Email content");
            });
        }

        executor.shutdown();
    }

//    public void sendBulkByHotMails(String email, String subject, String text) {
//        ExecutorService executor = Executors.newFixedThreadPool(10); // Sử dụng 10 luồng
//
//        emailService.sendEmail(email, subject, text);
//
//        executor.shutdown();
//
//    }
}