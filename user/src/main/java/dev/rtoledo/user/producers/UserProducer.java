package dev.rtoledo.user.producers;

import dev.rtoledo.user.dtos.EmailDTO;
import dev.rtoledo.user.models.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

  final RabbitTemplate rabbitTemplate;

  public UserProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Value(value = "${broker.queue.email.name}")
  private String routingKey;

  public void publishMessageEmail(UserModel userModel) {
    var emailDTO = new EmailDTO();

    emailDTO.setUserId(userModel.getUserId());
    emailDTO.setEmailTo(userModel.getEmail());
    emailDTO.setSubject("Cadastro realizado com sucesso");
    emailDTO.setText(userModel.getName() + ", seja bem vindo(a)!\nAgradecemos o seu cadastro.");

    rabbitTemplate.convertAndSend("", routingKey, emailDTO);
  }
}
