package com.tofazzal.notification.controller;


import com.tofazzal.notification.constants.AppConstants;
import com.tofazzal.notification.service.NotificationService;
import com.tofazzal.notification.service.viewModel.kafka.EmailData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    KafkaTemplate<String, EmailData> emailDataKafkaTemplate;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationService notificationService;

    @PostMapping
    @KafkaListener(topics = AppConstants.TOPIC_EMAIL, groupId = AppConstants.KAFKA_GROUP_EMAIL, containerFactory = AppConstants.KAFKA_LISTENER_CONTAINER_FACTORY_EMAIL_DATA)
    public ResponseEntity<Boolean> sendEmailNotification(@RequestBody EmailData emailData) throws Exception {
        log.info("\nRequest to send email : {} \n", emailData);
        if (Objects.nonNull(emailData.getNotificationUserId()))
            simpMessagingTemplate.convertAndSend(AppConstants.WEBSOCKET_TOPIC_NOTIFICATION + emailData.getNotificationUserId(), Objects.nonNull(emailData.getNotificationDTO()) ? emailData.getNotificationDTO() : "");

        boolean isNotificationSent = notificationService.sendEmailNotification(emailData);
        if (isNotificationSent) {
            emailData.setSend(true);
            if (emailData.isBKashNotificationRequired()) {
                simpMessagingTemplate.convertAndSend(AppConstants.WEBSOCKET_TOPIC_BKASH_NOTIFICATION, true);
            }
        }

        return ResponseEntity.ok().body(isNotificationSent);
    }

    @KafkaListener(topics = AppConstants.KAFKA_TOPIC_REFRESH_REQUEST_STATUS, groupId = AppConstants.KAFKA_GROUP_EMAIL, containerFactory = AppConstants.KAFKA_LISTENER_CONTAINER_FACTORY_STRING)
    public ResponseEntity<Void> refreshRequestStatus(@RequestBody String requestId) {
        log.info("\n Refresh Request status to refresh page : {} \n", requestId);
        if (Objects.nonNull(requestId))
            simpMessagingTemplate.convertAndSend(AppConstants.WEBSOCKET_TOPIC_REFRESH_REQUEST_STATUS + requestId, requestId);
        return ResponseEntity.notFound().build();
    }

    @MessageMapping("/notification-message-mapping")
    @SendTo("/topic/notification")
    public void getTestNotification() {
        simpMessagingTemplate.convertAndSend("/topic/notification", "Notification received successfully");
    }
}
