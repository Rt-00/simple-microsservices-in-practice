package dev.rtoledo.email.consumers;

import dev.rtoledo.email.dtos.EmailRecordDTO;
import dev.rtoledo.email.models.EmailModel;
import dev.rtoledo.email.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

  final EmailService emailService;

  public EmailConsumer(EmailService emailService) {
    this.emailService = emailService;
  }

  @RabbitListener(queues = "${broker.queue.email.name}")
  public void listenEmailQueue(@Payload EmailRecordDTO emailRecordDTO) {
    var emailModel = new EmailModel();

    BeanUtils.copyProperties(emailRecordDTO, emailModel);

    emailService.sendEmail(emailModel);
  }
}
