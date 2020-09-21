package dev.oldboy.navita.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dev.oldboy.navita.auth.user.dto.RequestUserDto;
import dev.oldboy.navita.auth.user.models.User;
import dev.oldboy.navita.auth.user.repositories.UserRepository;
import dev.oldboy.navita.auth.user.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("User Service Tests")
public class UserServiceTest {
  
  @MockBean
  private UserRepository repository;
  
  @InjectMocks
  private UserServiceImpl service;
  
  @Test
  @DisplayName("Find user by id")
  void shouldReturnUserWhenPassAValidId() {
    Long id = 1L;
    
    Mockito.when(repository.findById(id))
      .thenReturn(Optional.of(new User(1L, "ADMIN", "admin@navita.com.br", "99999999999", "senha@123", LocalDateTime.now(), LocalDateTime.now())));
    
    User user = service.findUserById(id);
    
    assertThat(user).isNotNull();
    assertThat(user.getName()).isEqualTo("ADMIN");
  }
  
  @Test
  @DisplayName("Find user by Email")
  void shouldReturnUserWhenPassAValidEmail() {
    String email = "admin@navita.com.br";
    
    Mockito.when(repository.findByEmail(email))
      .thenReturn(Optional.of(new User(1L, "ADMIN", "admin@navita.com.br", "99999999999", "senha@123", LocalDateTime.now(), LocalDateTime.now())));
    
    User user = service.findUserByEmail(email);
    
    assertThat(user).isNotNull();
    assertThat(user.getName()).isEqualTo("ADMIN");
  }
  
  @Test
  @DisplayName("Throw error when not Find user by Id")
  void shouldThrowErroWhenNotFindUser() {
    Long id = 1L;
    
    Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
    
    try {
      User user = service.findUserById(id);
    }catch(Exception e) {
      assertTrue(e.getClass() == UsernameNotFoundException.class);
    }
  }
  
  @Test
  @DisplayName("Save user")
  void shouldShoulReturnUserWhenSave() {
    RequestUserDto resquest = new RequestUserDto(
        "Paulo Monteiro",
        "paulo.monteiro@navita.com.br",
        "36282332834",
        "Navita@1234"
    );
    
    User user = new User(
        null,
        resquest.getName(),
        resquest.getEmail(),
        resquest.getCpf(),
        resquest.getCpf(),
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
    
    User savedUser = service.addUser(resquest);    
    
    assertThat(savedUser).isNotNull();
    assertThat(savedUser.getName()).isEqualTo(user.getName());
  }
  
  @Test
  @DisplayName("Try Delete Admin user throw error")
  void shouldThrowErrorWhenTrysToDeleteAdmin() {
    String email = "admin@navita.com.br";
       
    Mockito.when(repository.findByEmail(email))
      .thenReturn(Optional.of(new User(1L, "ADMIN", "admin@navita.com.br", "99999999999", "senha@123", LocalDateTime.now(), LocalDateTime.now())));
    
    try {
      Boolean userDeleted = service.deleteUserByEmail(email);
    }catch(Exception e) {
      assertTrue(e.getClass() == IllegalArgumentException.class);
    }
  }
}
