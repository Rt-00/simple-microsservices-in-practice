package dev.rtoledo.user.services;

import dev.rtoledo.user.models.UserModel;
import dev.rtoledo.user.producers.UserProducer;
import dev.rtoledo.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  final UserRepository userRepository;
  final UserProducer userProducer;

  public UserService(UserRepository userRepository, UserProducer userProducer) {
    this.userRepository = userRepository;
    this.userProducer = userProducer;
  }

  @Transactional
  public UserModel save(UserModel userModel) {
    UserModel userModelSaved = userRepository.save(userModel);
    userProducer.publishMessageEmail(userModel);

    return userModel;
  }
}
