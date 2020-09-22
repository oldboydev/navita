package dev.oldboy.navita.parking.manager.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;import ch.qos.logback.core.pattern.color.MagentaCompositeConverter;
import dev.oldboy.navita.parking.manager.dtos.RequestManagerDto;
import dev.oldboy.navita.parking.manager.exception.ManagerNotFoundException;
import dev.oldboy.navita.parking.manager.exception.ParkingLotIsFullException;
import dev.oldboy.navita.parking.manager.exception.VehicleNotParkingException;
import dev.oldboy.navita.parking.manager.models.Manager;
import dev.oldboy.navita.parking.manager.models.ManagerVehicle;
import dev.oldboy.navita.parking.manager.repositories.ManagerRepository;
import dev.oldboy.navita.parking.manager.repositories.ManagerVehicleRepository;
import dev.oldboy.navita.parking.parkinglot.dto.RequestParkingLotDto;
import dev.oldboy.navita.parking.parkinglot.models.ParkingLot;
import dev.oldboy.navita.parking.vehicle.models.Vehicle;
import dev.oldboy.navita.parking.vehicle.services.VehicleService;

@Service
public class ManagerServiceImpl implements ManagerService {
  
  @Autowired
  private ManagerRepository repository;
  
  @Autowired
  private ManagerVehicleRepository managerVehicleRepository;
  
  @Autowired
  private VehicleService vehicleService;
  
  @Override
  public Boolean addManager(ParkingLot parkingLot) {
    Manager manager = new Manager();
    manager.setIdParkingLot(parkingLot.getId());
    manager.setSpacesMoto(parkingLot.getSpacesMoto());
    manager.setSpaceCars(parkingLot.getSpaceCars());
    manager.setSpacesMotoEmpty(parkingLot.getSpacesMoto());
    manager.setSpaceCarsEmpty(parkingLot.getSpaceCars());
    manager.setUpdateAt(LocalDateTime.now());
    manager.setCreatedAt(LocalDateTime.now());
    
    try {
      repository.saveAndFlush(manager);
    } catch (Exception e) {
      e.printStackTrace();
    }  
    
    return true;
  }
  
  public Boolean deleteManager(ParkingLot parkingLot) {
    Optional<Manager> foundManager = repository.findByIdParkingLot(parkingLot.getId());
    
    if(foundManager.isPresent()) {
      Manager manager = foundManager.get();
      
      repository.delete(manager);
    }
    
    return true;
  }
  
  /**
   * validate if can update vehicles spaces
   *
   * if update space size for a vehicle is less than size of empty spaces
   * it's no possible to update.
   *
   * @param updateParkingLot
   */
  @Override
  public Boolean verifyIfCanUpdateSpaces(ParkingLot updateParkingLot, RequestParkingLotDto parkingLotInfo) {
    Optional<Manager> foundManager = repository.findByIdParkingLot(updateParkingLot.getId());
    
    if(foundManager.isPresent()) {
      Manager manager = foundManager.get();
      
      if(parkingLotInfo.getSpacesMoto() < manager.getSpacesMotoEmpty()) {
        throw new IllegalArgumentException(
            "It's not possible to downgrade total spaces for motos because there is no empty spaces!"
        );
      }
      
      if(parkingLotInfo.getSpaceCars() < manager.getSpaceCarsEmpty()) {
        throw new IllegalArgumentException(
            "It's not possible to downgrade total spaces for cars because there is no empty spaces!"
        );
      }      
    }
    
    return true;
  }
  
  /**
   * validate if can delete a parking lot
   * 
   * if some space is occupied its not possible to delete
   * 
   * @param deleteParkingLot
   */
  @Override
  public Boolean verifyIfCanDeleteParkingLot(ParkingLot deleteParkingLot) {
    Optional<Manager> foundManager = repository.findByIdParkingLot(deleteParkingLot.getId());
    
    if(foundManager.isPresent()) {
      Manager manager = foundManager.get();
      
      if(manager.getSpacesMotoEmpty() < manager.getSpacesMoto()) {
        String msg = "It's not possible to delete parking lot(" + deleteParkingLot.getId() + "): " +
            deleteParkingLot.getName() + ". There is some occupied moto spaces, first empty all spaces!";     
        
        throw new IllegalArgumentException(msg);            
      }
      
      if(manager.getSpaceCarsEmpty() < manager.getSpaceCars()) {
        String msg = "It's not possible to delete parking lot(" + deleteParkingLot.getId() + "): " +
            deleteParkingLot.getName() + ". There is some occupied cars spaces, first empty all spaces!";     
        
        throw new IllegalArgumentException(msg);            
      }
    }
    
    return true;
  }
  
  /**
   * manage the parking operation
   */
  @Override
  public Boolean parkingVehicle(RequestManagerDto parkInfo) {
    //verify if manager for parking lot exist
    Optional<Manager> foundManager = repository.findByIdParkingLot(parkInfo.getIdParkingLot());
    
    if(foundManager.isEmpty()) {
      throw new ManagerNotFoundException("There is no parking lot with id: " + parkInfo.getIdParkingLot());
    }
    
    Manager manager = foundManager.get();
    //verify if vehicle exist
    Vehicle vehicle = vehicleService.findById(parkInfo.getIdVehicle());
    Boolean parkingSuccess;
    //unparking
    if(parkInfo.getIdOperation() == 0) {
      parkingSuccess = unparkingVehicle(manager, vehicle);
    }else {
      parkingSuccess = parkingVehicle(manager, vehicle);
    }   
    
    repository.save(manager);
    return parkingSuccess;
  }
  
  /**
   * do unparking operation and adjust spaces
   * @param manager
   * @param vehicle
   * @return
   */
  private Boolean unparkingVehicle(Manager manager, Vehicle vehicle) {
    //verify if vehicle is parking
    Optional<ManagerVehicle> foundManagerVehicle = 
        managerVehicleRepository.findByIdManagerAndIdVehicle(manager.getId(), vehicle.getId());
    
    if(foundManagerVehicle.isEmpty()) {
      throw new VehicleNotParkingException("Vehicle with id: " + vehicle.getId() + " is not parking on parking lot with id: " + manager.getIdParkingLot());
    }
    
    return deleteManagerVehicle(manager, foundManagerVehicle.get(), vehicle.getType());
  }
  
  private Boolean parkingVehicle(Manager manager, Vehicle vehicle) {
    //verify if vehicle is parking on another parking lot
    Optional<ManagerVehicle> foundManagerVehicle = 
        managerVehicleRepository.findByIdVehicle(vehicle.getId());
    
    if(foundManagerVehicle.isPresent()) {
      throw new IllegalArgumentException("Vehicle with id: " + vehicle.getId() + " is already parking on another parking lot");
    }
    
    if(validateIfIsEmptySpacesByType(manager, vehicle)) {
      return saveManagerVehicle(manager, vehicle);
    }else {
      throw new ParkingLotIsFullException("Parking lot with id: " + manager.getIdParkingLot() + " is full");
    }
  }
  
  private Boolean saveManagerVehicle(Manager manager, Vehicle vehicle) {
    ManagerVehicle managerVehicle = new ManagerVehicle();
    managerVehicle.setIdManager(manager.getId());
    managerVehicle.setIdVehicle(vehicle.getId());
    managerVehicle.setUpdateAt(LocalDateTime.now());
    managerVehicle.setCreatedAt(LocalDateTime.now());
    
    ManagerVehicle savedManagerVehicle = managerVehicleRepository.save(managerVehicle);
    
    if(vehicle.getType() == 1) {
      manager.setSpacesMotoEmpty(manager.getSpacesMotoEmpty() - 1);
    }else {
      manager.setSpaceCarsEmpty(manager.getSpaceCarsEmpty() - 1);
    }
    
    return true;
  }
  
  private Boolean deleteManagerVehicle(Manager manager, ManagerVehicle managerVehicle, Integer vehicleType) {
    managerVehicleRepository.delete(managerVehicle);
    
    //verify the vehicle type and adjust spaces
    if(vehicleType == 1) {
      manager.setSpacesMotoEmpty(manager.getSpacesMotoEmpty() + 1);
      
      if(manager.getSpacesMotoEmpty() > manager.getSpacesMoto()) {
        manager.setSpacesMotoEmpty(manager.getSpacesMoto());
      }
    }else {
      manager.setSpaceCarsEmpty(manager.getSpaceCarsEmpty() + 1);
      
      if(manager.getSpaceCarsEmpty() > manager.getSpaceCars()) {
        manager.setSpaceCarsEmpty(manager.getSpaceCars());
      }
    }
    
    return true;
  }
  
  private Boolean validateIfIsEmptySpacesByType(Manager manager, Vehicle vehicle) {
    Integer type = vehicle.getType();
    Integer emptySpaces;
    
    if(type == 1) {
      emptySpaces = manager.getSpacesMotoEmpty();
    }else {
      emptySpaces = manager.getSpaceCarsEmpty();
    }
    
    return emptySpaces > 0;
  }

}
