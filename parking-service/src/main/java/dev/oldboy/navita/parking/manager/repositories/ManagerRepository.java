package dev.oldboy.navita.parking.manager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.oldboy.navita.parking.manager.models.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
  Optional<Manager> findByIdParkingLot(Long id);
}
