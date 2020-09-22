package dev.oldboy.navita.parking.manager.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.oldboy.navita.parking.manager.models.Manager;
import dev.oldboy.navita.parking.manager.repositories.ManagerRepository;
import dev.oldboy.navita.parking.parkinglot.models.ParkingLot;

@Service
public class ManagerServiceImpl implements ManagerService {
  
  @Autowired
  private ManagerRepository repository;
  
  @Override
  public void addManager(ParkingLot parkingLot) {
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
  }
  
  public void deleteManager(ParkingLot parkingLot) {
    Optional<Manager> foundManager = repository.findByIdParkingLot(parkingLot.getId());
    
    if(foundManager.isPresent()) {
      Manager manager = foundManager.get();
      
      repository.delete(manager);
    }
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
  public void verifyIfCanUpdateSpaces(ParkingLot updateParkingLot) {
    Optional<Manager> foundManager = repository.findByIdParkingLot(updateParkingLot.getId());
    
    if(foundManager.isPresent()) {
      Manager manager = foundManager.get();
      
      if(updateParkingLot.getSpacesMoto() < manager.getSpacesMotoEmpty()) {
        throw new IllegalArgumentException(
            "It's not possible to downgrade total spaces for motos because there is no empty spaces!"
        );
      }
      
      if(updateParkingLot.getSpaceCars() < manager.getSpaceCarsEmpty()) {
        throw new IllegalArgumentException(
            "It's not possible to downgrade total spaces for cars because there is no empty spaces!"
        );
      }
    }
  }
  
  /**
   * validate if can delete a parking lot
   * 
   * if some space is occupied its not possible to delete
   * 
   * @param deleteParkingLot
   */
  @Override
  public void verifyIfCanDeleteParkingLot(ParkingLot deleteParkingLot) {
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
  }

}
