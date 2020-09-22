package dev.oldboy.navita.parking.parkinglot.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.oldboy.navita.parking.manager.services.ManagerService;
import dev.oldboy.navita.parking.parkinglot.dto.RequestParkingLotDto;
import dev.oldboy.navita.parking.parkinglot.exceptions.ParkingLotNotFoundException;
import dev.oldboy.navita.parking.parkinglot.models.ParkingLot;
import dev.oldboy.navita.parking.parkinglot.repositories.ParkingLotRepository;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
  
  @Autowired
  private ParkingLotRepository repository;
  
  @Autowired
  private ManagerService managerService;
  
  @Override
  public List<ParkingLot> findAll() {
    List<ParkingLot> parkingLots = repository.findAll();
    
    if(parkingLots.size() == 0) {
      throw new ParkingLotNotFoundException("There is no parkingLots recorded");
    }
    
    return parkingLots;
  }

  @Override
  public ParkingLot findById(Long id) {
    Optional<ParkingLot> foundParkingLot = repository.findById(id);
    
    if(foundParkingLot.isPresent()) {
      return foundParkingLot.get();
    }
    
    throw new ParkingLotNotFoundException("Parking lot with id: " + id + " does not exist.");
  }

  @Override
  public ParkingLot findByCnpf(String cnpj) {
    Optional<ParkingLot> foundParkingLot = repository.findByCnpj(cnpj);
    
    if(foundParkingLot.isPresent()) {
      return foundParkingLot.get();
    }
    
    throw new ParkingLotNotFoundException("Parking lot with CNPJ: " + cnpj + " does not exist.");
  }

  @Override
  public ParkingLot addParkingLot(RequestParkingLotDto parkingLotInfo) {
    ParkingLot parkingLot = new ParkingLot(
        null,
        parkingLotInfo.getName(),
        parkingLotInfo.getCnpj(),
        parkingLotInfo.getAddress(),
        parkingLotInfo.getPhone(),
        parkingLotInfo.getSpacesMoto(),
        parkingLotInfo.getSpaceCars(),
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    ParkingLot savedParkingLot = repository.save(parkingLot);
    managerService.addManager(savedParkingLot);
    
    return savedParkingLot;
  }

  public ParkingLot updatePakingLot (RequestParkingLotDto parkingLotInfo, Long id) {
    ParkingLot updateParkingLot = findById(id);
    
    //validate before update    
    managerService.verifyIfCanUpdateSpaces(updateParkingLot, parkingLotInfo);
    
    updateParkingLot.setName(parkingLotInfo.getName());
    updateParkingLot.setCnpj(parkingLotInfo.getCnpj());
    updateParkingLot.setAddress(parkingLotInfo.getAddress());
    updateParkingLot.setPhone(parkingLotInfo.getPhone());
    updateParkingLot.setSpacesMoto(parkingLotInfo.getSpacesMoto());
    updateParkingLot.setSpaceCars(parkingLotInfo.getSpaceCars());
    updateParkingLot.setUpdateAt(LocalDateTime.now());
    
    return repository.saveAndFlush(updateParkingLot);
  }
  
  @Override
  public Boolean deleteById(Long id) {
    ParkingLot foundParkingLot = findById(id);
    
    //validate before delete
    managerService.verifyIfCanDeleteParkingLot(foundParkingLot);
    
    repository.delete(foundParkingLot);
    managerService.deleteManager(foundParkingLot);
    
    return true;
  }

  @Override
  public Boolean deleteByCnpj(String cnpj) {
    ParkingLot foundParkingLot = findByCnpf(cnpj);
    
    //validate before delete
    managerService.verifyIfCanDeleteParkingLot(foundParkingLot);
    managerService.deleteManager(foundParkingLot);
    
    repository.delete(foundParkingLot);
    
    return true;
  }
}
