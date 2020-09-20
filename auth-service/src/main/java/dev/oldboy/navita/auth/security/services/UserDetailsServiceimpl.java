package dev.oldboy.navita.auth.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.oldboy.navita.auth.models.User;
import dev.oldboy.navita.auth.repositories.UserRepository;
import dev.oldboy.navita.auth.security.models.UserDetailsImpl;

@Service
public class UserDetailsServiceimpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByEmail(username);
    
    if(user.isEmpty()) {
      throw new UsernameNotFoundException("We not found user with username/email: " + username);
    }
    
    return user.map(UserDetailsImpl::new).get();
  }
}
