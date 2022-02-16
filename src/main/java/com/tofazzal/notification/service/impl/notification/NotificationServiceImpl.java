package com.tofazzal.notification.service.impl.notification;

import com.tofazzal.notification.service.EmailService;
import com.tofazzal.notification.service.NotificationService;
import com.tofazzal.notification.service.viewModel.EmailContent;
import com.tofazzal.notification.service.viewModel.kafka.EmailData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final EmailService emailService;
    @Override
    public boolean sendEmailNotification(EmailData emailData) throws MessagingException {
        boolean isSent = false;
        EmailContent emailContent = new EmailContent();
        emailContent.setToEmail(emailData.getEmail());
        emailContent.setSubject(emailData.getSubject());
        emailContent.setAbsolutePathToFile(emailData.getAbsoluteFilePaths() == null ? new ArrayList<>() : emailData.getAbsoluteFilePaths());
        emailContent.setBody(emailData.getBody());
        if(Objects.nonNull(emailData.getCc()) && !emailData.getCc().equals("")) {
            String[] mail = emailData.getCc().split(",");
            String[] email = Arrays.asList(mail).stream().filter(e -> !e.equals(",") && !e.equals("") && Objects.nonNull(e)).toArray(String[]::new);
            if (Objects.nonNull(email)) {
                emailContent.setCc(email);
            }
        }
        return emailService.sendEmail(emailContent);
    }
}
