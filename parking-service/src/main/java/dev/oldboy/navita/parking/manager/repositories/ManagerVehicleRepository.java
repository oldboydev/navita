package dev.oldboy.navita.parking.manager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.oldboy.navita.parking.manager.models.ManagerVehicle;

public interface ManagerVehicleRepository extends JpaRepository<ManagerVehicle, Long> {
  Optional<ManagerVehicle> findByIdManagerAndIdVehicle(Long idManager, Long idVehicle);

  Optional<ManagerVehicle> findByIdVehicle(Long id);
}
