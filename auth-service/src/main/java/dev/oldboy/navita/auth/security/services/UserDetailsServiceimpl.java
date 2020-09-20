package dev.oldboy.navita.auth.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.oldboy.navita.auth.models.User;
import dev.oldboy.navita.auth.repositories.UserRepository;
import dev.oldboy.navita.auth.security.models.TokenInfo;
import dev.oldboy.navita.auth.security.models.UserDetailsImpl;
import dev.oldboy.navita.auth.security.utils.JwtTokenUtils;

@Service
public class UserDetailsServiceimpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;
  
  private UserDetails userDetails;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByEmail(username);
    
    if(user.isEmpty()) {
      throw new UsernameNotFoundException("We not found user with username/email: " + username);
    }
    
    userDetails = user.map(UserDetailsImpl::new).get();
    return userDetails;
  }
  
  public TokenInfo generateToken() {
    JwtTokenUtils jwtTokenUtils = new JwtTokenUtils();
    TokenInfo tokenInfo = new TokenInfo();
    
    tokenInfo.setType("Bearer");
    tokenInfo.setToken(jwtTokenUtils.generateToken(userDetails));
    tokenInfo.setExpiresIn(jwtTokenUtils.getExpirationTime());
    
    return tokenInfo;
  }
}
