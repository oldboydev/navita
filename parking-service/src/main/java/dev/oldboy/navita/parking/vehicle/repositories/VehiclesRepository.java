package dev.oldboy.navita.parking.vehicle.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.oldboy.navita.parking.vehicle.models.Vehicle;

public interface VehiclesRepository extends JpaRepository<Vehicle, Long> {

  Optional<Vehicle> findByPlate(String plate);

}
