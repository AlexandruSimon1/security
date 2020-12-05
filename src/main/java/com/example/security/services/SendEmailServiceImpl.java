package com.example.security.services;

import com.example.security.model.MailModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
@Service
public class SendEmailServiceImpl implements SendEmailService {
    private final JavaMailSender emailSender;
    private final Configuration emailConfig;
    @Value("${email.from}")
    private String from;

    public SendEmailServiceImpl(JavaMailSender emailSender,@Qualifier(value = "emailConfigBean") Configuration emailConfig) {
        this.emailSender = emailSender;
        this.emailConfig = emailConfig;
    }

    @Override
    public void send(MailModel mailModel) throws MessagingException, IOException, TemplateException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Template template = emailConfig.getTemplate(mailModel.getTemplate());
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getContent());
        mimeMessageHelper.setTo(mailModel.getTo());
        mimeMessageHelper.setText(html, true);
        mimeMessageHelper.setFrom(this.from);
        mimeMessageHelper.setSubject(mailModel.getSubject());
        if (mailModel.getCc()!=null){
            mimeMessageHelper.setCc(mailModel.getCc());
        }
        if(mailModel.getBcc()!=null){
            mimeMessageHelper.setBcc(mailModel.getBcc());
        }
        emailSender.send(message);
    }

    @Override
    public void sendEmail(MailModel mailModel,String user, String email) throws Exception {
//        Map model = new HashMap();
//        model.put("name",user);
//
//        mailModel.setModel(model);
//        MimeMessage message = emailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
//        Template template = emailConfig.getTemplate("email.ftl.html");
//        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getModel());
//        mimeMessageHelper.setTo(email);
//        mimeMessageHelper.setText(html, true);
//        mimeMessageHelper.setFrom(mailModel.getFrom());
//        emailSender.send(message);
    }
}
