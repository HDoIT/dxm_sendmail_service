package com.dospc.spc.smail.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MultiSmtpEmailService {

    private final List<JavaMailSender> mailSenders;
    private final List<Integer> mailLimits;
    private final List<String> senderEmails; // Danh sách địa chỉ email người gửi
    private final List<String> senderNames;
    private final AtomicInteger currentMailSenderIndex = new AtomicInteger(0);
    private final int[] emailCounts;

    @Autowired
    public MultiSmtpEmailService(
            List<JavaMailSender> mailSenders,
            List<Integer> mailLimits,
            List<String> senderEmails, List<String> senderNames) {
        this.mailSenders = mailSenders;
        this.mailLimits = mailLimits;
        this.senderEmails = senderEmails;
        this.emailCounts = new int[mailSenders.size()];
        this.senderNames = senderNames;
    }
    public void sendEmail(String to, String subject, String text) {
        int index = getNextAvailableMailSenderIndex();
        if (index == -1) {
            throw new RuntimeException("All mail services have reached their daily limit.");
        }

        JavaMailSender mailSender = mailSenders.get(index);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(formatSender(senderNames.get(index), senderEmails.get(index))); // Đặt tên và email người gửi
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

        emailCounts[index]++;
    }

    private String formatSender(String name, String email) {
        return String.format("%s <%s>", name, email); // Định dạng: "Tên Người Gửi <email@example.com>"
    }

    private int getNextAvailableMailSenderIndex() {
        for (int i = 0; i < mailSenders.size(); i++) {
            int index = (currentMailSenderIndex.getAndIncrement() + i) % mailSenders.size();
            if (emailCounts[index] < mailLimits.get(index)) {
                return index;
            }
        }
        return -1;
    }
}