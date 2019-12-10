package com.fuhana.interfaces;

import com.fuhan.commons.pojo.ResultBean;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

public interface IMailService {


    void sendSimpleTextMail(String to,String subject,String content);
    void sendHtmlMail(String to,String subject,String content) ;
    void sendAttachmentMail(String to,String subject,String content,String filePath);
    void sendTemplateMail();

}


