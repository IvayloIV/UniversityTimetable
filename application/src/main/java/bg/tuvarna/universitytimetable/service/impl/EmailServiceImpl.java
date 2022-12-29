package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.service.EmailService;
import bg.tuvarna.universitytimetable.util.ResourceBundleUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.expression.ThymeleafEvaluationContext;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final ResourceBundleUtil resourceBundleUtil;
    private final ApplicationContext applicationContext;

    @Autowired
    public EmailServiceImpl(TemplateEngine templateEngine,
                            JavaMailSender javaMailSender,
                            ResourceBundleUtil resourceBundleUtil,
                            ApplicationContext applicationContext) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.resourceBundleUtil = resourceBundleUtil;
        this.applicationContext = applicationContext;
    }

    @Override
    public void sendScheduleNotifications(String language, List<String> emails, Resource attachment) throws IOException {
        String subject = resourceBundleUtil.getMessage("scheduleMail.subject", language);
        String html = templateEngine.process("mails/studentSchedule", createContext(language));
        byte[] attachmentBytes = IOUtils.toByteArray(attachment.getInputStream());
        emails.forEach(e -> sendMail(html, e, subject, attachmentBytes));
    }

    private void sendMail(String content, String to, String subject, byte[] attachmentBytes) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setText(content, true);
            helper.addAttachment("Timetable.pdf", new ByteArrayResource(attachmentBytes));
            helper.setSubject(subject);
            helper.setTo(to);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
    }

    private Context createContext(String language) {
        Context context = new Context();
        context.setVariable(ThymeleafEvaluationContext.THYMELEAF_EVALUATION_CONTEXT_CONTEXT_VARIABLE_NAME,
                new ThymeleafEvaluationContext(applicationContext, null));
        context.setVariable("language", language);
        return context;
    }
}
