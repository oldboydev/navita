package dev.oldboy.navita.parking.vehicle.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.oldboy.navita.parking.vehicle.dtos.RequestVehicleLotDto;
import dev.oldboy.navita.parking.vehicle.exceptions.VehicleNotFoundException;
import dev.oldboy.navita.parking.vehicle.models.Vehicle;
import dev.oldboy.navita.parking.vehicle.repositories.VehiclesRepository;

@Service
public class VehicleServiceImpl implements VehicleService {

  @Autowired
  private VehiclesRepository repository;
  
  @Override
  public List<Vehicle> findAll() {
    List<Vehicle> vehicles = repository.findAll();
    
    if(vehicles.size() == 0) {
      throw new RuntimeException("There is no parkingLots recorded");
    }
    
    return vehicles;
  }

  @Override
  public Vehicle findById(Long id) {
    Optional<Vehicle> foundVehicle = repository.findById(id);
    
    if(foundVehicle.isPresent()) {
      return foundVehicle.get();
    }
    
    throw new VehicleNotFoundException("Vehicle with id: " + id + " does not exist.");
  }

  @Override
  public Vehicle findByPlate(String plate) {
    Optional<Vehicle> foundVehicle = repository.findByPlate(plate);
    
    if(foundVehicle.isPresent()) {
      return foundVehicle.get();
    }
    
    throw new VehicleNotFoundException("Vehicle with plate: " + plate + " does not exist.");
  }

  @Override
  public Vehicle addVehicle(RequestVehicleLotDto vehicleInfo) {
    Vehicle vehicle = new Vehicle(
        null,
        vehicleInfo.getBrand(),
        vehicleInfo.getModel(),
        vehicleInfo.getColor(),
        vehicleInfo.getPlate(),
        vehicleInfo.getType(),
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    return repository.save(vehicle);
  }

  @Override
  public Vehicle updateVehicle(RequestVehicleLotDto vehicleInfo, Long id) {
    Vehicle updateVehicle = findById(id);
    
    updateVehicle.setBrand(vehicleInfo.getBrand());
    updateVehicle.setModel(vehicleInfo.getModel());
    updateVehicle.setColor(vehicleInfo.getColor());
    updateVehicle.setPlate(vehicleInfo.getPlate());
    updateVehicle.setType(vehicleInfo.getType());
    updateVehicle.setUpdateAt(LocalDateTime.now());
    
    return repository.saveAndFlush(updateVehicle);
  }

  @Override
  public Boolean deleteById(Long id) {
    //validate if is parking before delete
    Vehicle foundVehicle = findById(id);
    
    repository.delete(foundVehicle);
    
    return true;
  }

  @Override
  public Boolean deleteByPlate(String plate) {
    //validate if is parking before delete
    Vehicle foundVehicle = findByPlate(plate);
    
    repository.delete(foundVehicle);
    
    return true;
  }

}
