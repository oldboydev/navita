package dev.oldboy.navita.auth.user.services;

import dev.oldboy.navita.auth.user.dto.RequestUserDto;
import dev.oldboy.navita.auth.user.models.User;

public interface UserService {
  public User findUserById(Long id);
  public User findUserByEmail(String email);
  
  public User addUser(RequestUserDto userInfo);
  
  public Boolean deleteUserById(Long id);
  public Boolean deleteUserByEmail(String email);
}
