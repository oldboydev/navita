package dev.oldboy.navita.auth.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.oldboy.navita.auth.user.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
  
  Optional<User> findByCpf(String email);
  
  void deleteByEmail(String email);
  
  void deleteByCpf(String email);
  
}
