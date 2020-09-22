package dev.oldboy.navita.parking.vehicle.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.oldboy.navita.parking.vehicle.models.Vehicle;

@Repository
public interface VehiclesRepository extends JpaRepository<Vehicle, Long> {

  Optional<Vehicle> findByPlate(String plate);
  
  void deleteByPlate(String plate);
}
