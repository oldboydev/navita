package dev.oldboy.navita.parking.manager.exception;

public class ParkingLotIsFullException extends RuntimeException {
  public ParkingLotIsFullException(String message) {
    super(message);
  }  
}
