package com.tofazzal.notification.service;

import com.tofazzal.notification.service.viewModel.kafka.EmailData;

import javax.mail.MessagingException;

public interface NotificationService {

    boolean sendEmailNotification(EmailData emailData) throws MessagingException;

}
