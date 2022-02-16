package com.tofazzal.notification.service.viewModel.kafka;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Md. Tofazzal Hossain\nDate : 07-07-2021
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmSData implements Serializable {

        private static final long serialVersionUID = 1744050117179344127L;

        private String appName;
        private String email;
        private String subject;
        private String body;
        private boolean bkashNotificationRequired;
}
