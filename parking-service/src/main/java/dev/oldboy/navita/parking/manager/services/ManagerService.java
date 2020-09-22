package dev.oldboy.navita.parking.manager.services;

import dev.oldboy.navita.parking.manager.dtos.RequestManagerDto;
import dev.oldboy.navita.parking.parkinglot.models.ParkingLot;

public interface ManagerService {
  public Boolean addManager(ParkingLot parkingLot);
  
  public Boolean deleteManager(ParkingLot parkingLot);
  
  public Boolean verifyIfCanUpdateSpaces(ParkingLot updateParkingLot);
  public Boolean verifyIfCanDeleteParkingLot(ParkingLot deleteParkingLot);
}
