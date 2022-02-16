package com.tofazzal.notification.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "default_info")
public class DefaultInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String messageType;
    @ManyToOne
    @JoinColumn(name="server_id", nullable=false)
    private ServerInfo serverInfo;

}
