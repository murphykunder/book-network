package com.alimurph.book.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendMail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String confirmationUrl,
            String activationCode,
            String subject
    ) throws MessagingException {

        String templateName="";
        if(emailTemplate == null)
            templateName = "confirm-email";
        else
            templateName = emailTemplate.getName();

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );

        Map<String, Object> messageProperties = new HashMap<>();
        messageProperties.put("username", username);
        messageProperties.put("confirmation_url", confirmationUrl);
        messageProperties.put("activation_code", activationCode);

        Context context = new Context();
        context.setVariables(messageProperties);  // pass the variables to be used in the message template

        mimeMessageHelper.setFrom("contact@alimurph.com");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);

        // default location where thymeleaf ans spring will look for templates is in resources/templates folder
        String messageTemplate = templateEngine.process(templateName, context);

        mimeMessageHelper.setText(messageTemplate, true);  // mention if the message is of html format or not

        mailSender.send(mimeMessage);

    }
}
