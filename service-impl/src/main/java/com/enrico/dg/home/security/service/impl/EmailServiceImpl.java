package com.enrico.dg.home.security.service.impl;

import com.enrico.dg.home.security.entity.constant.enums.ResponseCode;
import com.enrico.dg.home.security.libraries.exception.BusinessLogicException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Component
public class EmailServiceImpl {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

  public void sendMail() {
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("gerichocoblaster@gmail.com", "geri5555");
      }
    });

    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("gerichocoblaster@gmail.com", false));
      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("billforcedbysome1tocreatethis@gmail.com"));
      message.setSubject("Potential Intruder Alert!");
      message.setContent("Please Check Your Notification ASAP", "text/html");
      message.setSentDate(new Date());

      Transport.send(message);
    } catch (MessagingException e) {
      LOGGER.error(e.getMessage());
      throw new BusinessLogicException(ResponseCode.SYSTEM_ERROR.getCode(),
              ResponseCode.SYSTEM_ERROR.getMessage());
    }
  }
}