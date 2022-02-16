package com.tofazzal.notification.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "server_info")
public class ServerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String smtpUsername;
    private String smtpPassword;
    private String smtpHost;
    private int smtpPort;
    private boolean smtpSecurityProtocol;
    private int smtpSocketFactoryPort;
    private boolean smtpSocketfactoryFallback;
    private boolean smtpTlsEnabled;
    private boolean smtpAuthentication;
    private boolean smtpDebug;

    //    @Enumerated(value = EnumType.STRING)
//    @Column(length = 15)
//    private MessageType messageType;
}
