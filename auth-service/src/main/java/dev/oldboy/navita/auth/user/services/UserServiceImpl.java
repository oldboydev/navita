package dev.oldboy.navita.auth.user.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.oldboy.navita.auth.user.dto.RequestUserDto;
import dev.oldboy.navita.auth.user.models.User;
import dev.oldboy.navita.auth.user.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
  
  @Autowired
  private UserRepository repository;
  
  @Override
  public List<User> findAll() {
    List<User> users = repository.findAll();
    
    if(users.size() == 0) {
      throw new RuntimeException("There is no users recod");
    }
    
    return users;
  }
  
  @Override
  public User findUserById(Long id) {
    Optional<User> foundUser = repository.findById(id);
    
    if(foundUser.isPresent()) {
      return foundUser.get();
    }
    
    throw new UsernameNotFoundException("User with id: " + id + " does not exist.");
  }

  @Override
  public User findUserByEmail(String email) {
    Optional<User> foundUser = repository.findByEmail(email);
    
    if(foundUser.isPresent()) {
      return foundUser.get();
    }
    
    throw new UsernameNotFoundException("User with email: " + email + " does not exist.");
  }

  @Override
  public User addUser(RequestUserDto userInfo) {
    //before build user object is needed more complex validations
    User user = new User(
        null,
        userInfo.getName(),
        userInfo.getEmail(),
        userInfo.getCpf(),
        userInfo.getCpf(),
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    return repository.save(user);
  }

  @Override
  public Boolean deleteUserById(Long id) {
    User foundUser = findUserById(id);
    
    verifyIfUserCanBeDelete(foundUser);
    
    repository.delete(foundUser);    
    
    return true;
  }

  @Override
  public Boolean deleteUserByEmail(String email) {
    User foundUser = findUserByEmail(email);
    
    verifyIfUserCanBeDelete(foundUser);
    
    repository.delete(foundUser);    
    
    return true;
  }
  
  //admin user is the master user, when using roles this will be changed
  //for checked role first and if user have a field canBeDelete
  private void verifyIfUserCanBeDelete(User user) {
    if(user.getName().equals("ADMIN")) {
      throw new IllegalArgumentException("ADMIN user cannot be delete");
    }
  }  
}
