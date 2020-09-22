package dev.oldboy.navita.parking.parkinglot.service;

import java.util.List;

import dev.oldboy.navita.parking.parkinglot.dto.RequestParkingLotDto;
import dev.oldboy.navita.parking.parkinglot.models.ParkingLot;

public interface ParkingLotService {
  public List<ParkingLot> findAll();
  public ParkingLot findById(Long id);
  public ParkingLot findByCnpf(String cnpj);
  
  public ParkingLot addParkingLot(RequestParkingLotDto parkingLotInfo);
  public ParkingLot updatePakingLot (RequestParkingLotDto parkingLotInfo, Long id);
  
  public Boolean deleteById(Long id);
  public Boolean deleteByCnpj(String cnpj);
}
