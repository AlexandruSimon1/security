package com.example.security.model;

import java.util.Map;

public class MailModel {
    private String from;
    private String to;
    private String subject;
    private Map<String,String> content;
    private String bcc;
    private String cc;
    private String template;

    public MailModel(String from, String to, String subject, Map<String, String> content, String bcc, String cc, String template) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.bcc = bcc;
        this.cc = cc;
        this.template = template;
    }

    public MailModel() {
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, String> getContent() {
        return content;
    }

    public void setContent(Map<String, String> content) {
        this.content = content;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
