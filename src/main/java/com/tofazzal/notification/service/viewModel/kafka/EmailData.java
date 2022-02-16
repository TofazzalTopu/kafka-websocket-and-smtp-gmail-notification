package com.tofazzal.notification.service.viewModel.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

/**
 * @author Md. Tofazzal Hossain\nDate : 07-07-2021
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailData implements Serializable{

    private static final long serialVersionUID = 1744050117179344127L;

    private Integer id;
    private String appName;
    private String email;
    private String cc;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String body;
    private Integer organizationId;
    private Integer notificationUserId;
    private boolean bKashNotificationRequired;
    private boolean isSend;
    private String redirectUrl;
    private String notificationDTO;
    private List<String> absoluteFilePaths;

}
