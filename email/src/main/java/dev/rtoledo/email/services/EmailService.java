package dev.rtoledo.email.services;

import dev.rtoledo.email.enums.StatusEmail;
import dev.rtoledo.email.models.EmailModel;
import dev.rtoledo.email.repositories.EmailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

  final EmailRepository emailRepository;
  final JavaMailSender emailSender;

  public EmailService(EmailRepository emailRepository, JavaMailSender emailSender) {
    this.emailRepository = emailRepository;
    this.emailSender = emailSender;
  }

  @Value(value = "${spring.mail.username")
  private String emailFrom;

  @Transactional
  public EmailModel sendEmail(EmailModel emailModel) {
    EmailModel savedEmail = null;
    try {
      emailModel.setSendDateEmail(LocalDateTime.now());
      emailModel.setEmailFrom(emailFrom);

      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo(emailModel.getEmailTo());
      message.setSubject(emailModel.getSubject());
      message.setText(emailModel.getText());
      emailSender.send(message);

      emailModel.setStatusEmail(StatusEmail.SENT);
    } catch (MailException e) {
      emailModel.setStatusEmail(StatusEmail.ERROR);
    } finally {
      savedEmail = emailRepository.save(emailModel);
    }

    return savedEmail;
  }
}
