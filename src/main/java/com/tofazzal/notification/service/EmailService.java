package com.tofazzal.notification.service;

import com.tofazzal.notification.service.viewModel.EmailContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.Serializable;
import java.util.Objects;

@Slf4j
@Service
public class EmailService implements Serializable {
    private static final long serialVersionUID = 1L;

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    String fromEmail;

    @Value("${core.api.base.upload.path}")
    String CORE_API_BASE_UPLOAD_PATH;

    public boolean sendEmail(EmailContent emailContent) throws MessagingException {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            message.setSubject(emailContent.getSubject());
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setSubject(emailContent.getSubject());
            log.info("Subject:  {}", emailContent.getSubject());
            helper.setFrom(fromEmail);
            if (Objects.nonNull(emailContent.getCc()) && emailContent.getCc().length > 0)
                helper.setCc(emailContent.getCc());
            helper.setTo(emailContent.getToEmail());
            helper.setText(emailContent.getBody(), true);
            if (!emailContent.getAbsolutePathToFile().isEmpty()) {
                for (String absolutePathToFile : emailContent.getAbsolutePathToFile()) {
                    FileSystemResource file = new FileSystemResource(new File(CORE_API_BASE_UPLOAD_PATH + File.separator + absolutePathToFile));
                    helper.addAttachment(file.getFilename(), file);
                }
            }
            emailSender.send(message);
            log.info("\n \n Email sent successfully to {} with subject {} \n", emailContent.getToEmail(), emailContent.getSubject());
            return true;
        } catch (Exception e) {
            log.error("\n \n Email sent failed to {} with subject {}, {}", emailContent.getToEmail(), emailContent.getSubject(), e.getMessage());
        }
        return false;
    }
}
