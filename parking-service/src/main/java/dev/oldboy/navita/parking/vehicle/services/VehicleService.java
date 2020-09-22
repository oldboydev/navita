package dev.oldboy.navita.parking.vehicle.services;

import java.util.List;

import dev.oldboy.navita.parking.vehicle.dtos.RequestVehicleLotDto;
import dev.oldboy.navita.parking.vehicle.models.Vehicle;

public interface VehicleService {
  public List<Vehicle> findAll();
  public Vehicle findById(Long id);
  public Vehicle findByPlate(String plate);
  
  public Vehicle addVehicle(RequestVehicleLotDto vehicleInfo);
  public Vehicle updateVehicle (RequestVehicleLotDto vehicleInfo, Long id);
  
  public Boolean deleteById(Long id);
  public Boolean deleteByPlate(String plate);
}
