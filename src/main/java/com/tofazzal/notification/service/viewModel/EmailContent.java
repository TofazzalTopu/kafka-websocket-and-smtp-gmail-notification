package com.tofazzal.notification.service.viewModel;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class EmailContent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fromEmail;
    private String toEmail;
    private String[] cc;
    private String subject;
    private String body;
    private List<String> absolutePathToFile = new ArrayList<>();
    private boolean isTemplate = false;
    private String messageObject;
}
