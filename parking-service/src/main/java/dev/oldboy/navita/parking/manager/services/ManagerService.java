package dev.oldboy.navita.parking.manager.services;

import dev.oldboy.navita.parking.manager.dtos.RequestManagerDto;
import dev.oldboy.navita.parking.parkinglot.models.ParkingLot;

public interface ManagerService {
  public void addManager(ParkingLot parkingLot);
  
  public void deleteManager(ParkingLot parkingLot);
  
  public void verifyIfCanUpdateSpaces(ParkingLot updateParkingLot);
  public void verifyIfCanDeleteParkingLot(ParkingLot deleteParkingLot);
}
