package dev.oldboy.navita.parking.parkinglot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.oldboy.navita.parking.parkinglot.models.ParkingLot;
@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {

  Optional<ParkingLot> findByCnpj(String cnpj);

  void deleteByCnpj(String cnpj);

}
