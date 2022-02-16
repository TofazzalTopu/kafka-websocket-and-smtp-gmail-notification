package com.tofazzal.notification.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "templates")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String subject;
    private String body;
//    private List<String> attachmentList;
}
