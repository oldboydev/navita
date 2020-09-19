package dev.oldboy.navita.auth;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import dev.oldboy.navita.auth.models.User;
import dev.oldboy.navita.auth.repositories.UserRepository;

@TestPropertySource(locations = "classpath:test.yml")
@DataJpaTest
@DisplayName("User Persistence Tests")
public class UserPersistenceTest {
  
  @Autowired
  private UserRepository repository;
  
  @BeforeEach
  void setUp() {
    User admin = new User(
        null,
        "Paulo Monteiro",
        "paulo.monteiro@navita.com.br",
        "36282332834",
        "Navita@1234",
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    User user = new User(
        null,
        "Heitor Carlos Eduardo Almada",
        "heitor.almada@gmail.com",
        "11162084160",
        "heitor!_12",
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    repository.save(admin);
    repository.save(user);
    
    repository.flush();
  }
  
  @Test
  @DisplayName("Unique CPF")
  void shouldThrowExceptionWhenAddUserWithRepetableCPF() {
    User user = new User(
        null,
        "João Carlos",
        "joão.carlos@gmail.com",
        "36282332834",
        "123_joao",
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    try {
      User savedUser = repository.save(user);
      repository.flush();   
    }catch(Exception e) {
      assertTrue(e.getCause() instanceof ConstraintViolationException);
    }      
  }  
  
  @Test
  @DisplayName("Unique Email")
  void shouldThrowExceptionWhenAddUserWithRepetableEmail() {
    User user = new User(
        null,
        "João Carlos",
        "paulo.monteiro@navita.com.br",
        "36282332834",
        "123_joao",
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    try {
      User savedUser = repository.save(user);
      repository.flush();   
    }catch(Exception e) {
      assertTrue(e.getCause() instanceof ConstraintViolationException);
    }      
  }
  
  @Test
  @DisplayName("Insert valid user")
  void shouldReturnEntityWhenAddValidUser() {
    User user = new User(
        null,
        "João Carlos",
        "joão.carlos@gmail.com",
        "10482332834",
        "123_joao",
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    User savedUser = repository.save(user);
    repository.flush();   
   
    assertNotNull(savedUser.getId());
  }
  
  @Test
  @DisplayName("FindById")
  void shouldReturnUserWhenValidId() {
    User user = new User(
        null,
        "João Carlos",
        "joão.carlos@gmail.com",
        "10482332834",
        "123_joao",
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    User savedUser = repository.save(user);
    repository.flush();   
    
    Optional<User> foundUser = repository.findById(savedUser.getId());
    
    assertTrue(foundUser.isPresent());
  }
  
  @Test
  @DisplayName("FindByEmail")
  void shouldReturnUserWhenValidEmail() {  
    String email = "paulo.monteiro@navita.com.br";
    Optional<User> foundUser = repository.findByEmail(email);
    
    assertTrue(foundUser.isPresent());
  }
  
  @Test
  @DisplayName("FindByCpf")
  void shouldReturnUserWhenValidCpf() {  
    String cpf = "36282332834";
    Optional<User> foundUser = repository.findByCpf(cpf);
    
    assertTrue(foundUser.isPresent());
  }
  
  @Test
  @DisplayName("Delete user by entity")
  void shouldDeleteEntityWhenValidUser() {
    Long usersCountBefore = repository.count();
    
    String email = "paulo.monteiro@navita.com.br";
    Optional<User> foundUser = repository.findByEmail(email);
    User user = foundUser.get();
    
    repository.delete(user);
    
    Long usersCountAfter = repository.count();
    
    assertThat(usersCountAfter).isEqualTo(usersCountBefore - 1); 
  }
  
  @Test
  @DisplayName("Delete user by email")
  void shouldDeleteEntityWhenValidEmail() {
    Long usersCountBefore = repository.count();
    
    String email = "paulo.monteiro@navita.com.br";    
    repository.deleteByEmail(email);
    
    Long usersCountAfter = repository.count();
    
    assertThat(usersCountAfter).isEqualTo(usersCountBefore - 1); 
  }
  
  @Test
  @DisplayName("Delete user by cpf")
  void shouldDeleteEntityWhenValidCpf() {
    Long usersCountBefore = repository.count();
    
    String cpf = "36282332834"; 
    repository.deleteByCpf(cpf);
    
    Long usersCountAfter = repository.count();
    
    assertThat(usersCountAfter).isEqualTo(usersCountBefore - 1); 
  }
}


