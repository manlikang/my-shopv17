package com.fuhan.v17mailservice.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.fuhan.commons.pojo.ResultBean;
import com.fuhan.entity.ErrorEmail;
import com.fuhan.mapper.ErrorEmailMapper;
import com.fuhana.interfaces.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/11/8
 */
@Service
@org.springframework.stereotype.Service
@Slf4j
public class MailServiceImpl implements IMailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ErrorEmailMapper errorEmailMapper;

    @Value("${mail.fromAddr}")
    private String from;


    @Override
    public void sendSimpleTextMail(String to, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        mailSender.send(mailMessage);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content)  {
        MimeMessage message = mailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message,true);
                helper.setFrom(from);
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(content,true);
                mailSender.send(message);
            }catch (MessagingException e) {
                System.out.println("进入异常");
                errorEmailMapper.insertSelective(new ErrorEmail(null,to,subject,null,null,1));
                log.error(e.getMessage());
            }


    }

    @Override
    public void sendAttachmentMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            FileSystemResource fileSystemResource = new FileSystemResource(filePath);
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName,fileSystemResource);
            mailSender.send(message);
            System.out.println("发送邮件成功");
        }catch (MessagingException e){
            log.error(e.getMessage());
        }
    }

    @Override
    public void sendTemplateMail() {

    }

}
