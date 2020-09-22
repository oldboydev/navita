package dev.oldboy.navita.parking.parkinglot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.oldboy.navita.parking.manager.models.Manager;
import dev.oldboy.navita.parking.parkinglot.models.ParkingLot;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

  Optional<ParkingLot> findByCnpj(String cnpj);

}
