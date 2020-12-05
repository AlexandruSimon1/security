package com.example.security.services;

import com.example.security.model.MailModel;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface SendEmailService {
    void sendEmail(MailModel mailModel,String user, String email) throws Exception;
    void send(MailModel mailModel) throws MessagingException, IOException, TemplateException;
}
