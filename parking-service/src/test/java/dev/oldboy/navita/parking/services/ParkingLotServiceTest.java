package dev.oldboy.navita.parking.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dev.oldboy.navita.parking.manager.services.ManagerService;
import dev.oldboy.navita.parking.parkinglot.dto.RequestParkingLotDto;
import dev.oldboy.navita.parking.parkinglot.exceptions.ParkingLotNotFoundException;
import dev.oldboy.navita.parking.parkinglot.models.ParkingLot;
import dev.oldboy.navita.parking.parkinglot.repositories.ParkingLotRepository;
import dev.oldboy.navita.parking.parkinglot.service.ParkingLotServiceImpl;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Parking lot Service Tests")
public class ParkingLotServiceTest {
  
  @MockBean
  private ParkingLotRepository repository;
  
  @MockBean
  private ManagerService managerService;
  
  @InjectMocks
  private ParkingLotServiceImpl service;
  
  @Test
  @DisplayName("Find parking lot by id")
  void shouldReturnParkingLotWhenPassAValidId() {
    Long id = 1L;
    
    Mockito.when(repository.findById(id))
      .thenReturn(Optional.of(
          new ParkingLot(
            null,
            "Navita Parking1",
            "14264523123",
            "Rua teste 123",
            "111111111",
            2,
            4,
            LocalDateTime.now(),
            LocalDateTime.now()
        )));
    
    ParkingLot parkingLot1 = service.findById(id);
    
    assertThat(parkingLot1).isNotNull();
    assertThat(parkingLot1.getName()).isEqualTo("Navita Parking1");
  }
  
  @Test
  @DisplayName("Find parking lot by CNPJ")
  void shouldReturnParkingLotWhenPassAValidCnpj() {
    String cnpj = "14264523123";
    
    Mockito.when(repository.findByCnpj(cnpj))
    .thenReturn(Optional.of(
        new ParkingLot(
          null,
          "Navita Parking1",
          "14264523123",
          "Rua teste 123",
          "111111111",
          2,
          4,
          LocalDateTime.now(),
          LocalDateTime.now()
      )));
  
    ParkingLot parkingLot1 = service.findByCnpf(cnpj);
    
    assertThat(parkingLot1).isNotNull();
    assertThat(parkingLot1.getName()).isEqualTo("Navita Parking1");
  }
  
  @Test
  @DisplayName("Throw error when not Find parking lot by Id")
  void shouldThrowErroWhenNotFindParkingLot() {
    Long id = 1L;
    
    Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
    
    try {
      ParkingLot parkingLot1 = service.findById(id);;
    }catch(Exception e) {
      assertTrue(e.getClass() == ParkingLotNotFoundException.class);
    }
  }
  
  @Test
  @DisplayName("Save parking lot")
  void shouldReturnParkingLotWhenSave() {
    RequestParkingLotDto resquest = new RequestParkingLotDto(
        "Navita Parking",
        "14564523103",
        "Rua teste 123",
        "111111111",
        2,
        4
    );
    
    ParkingLot parkingLot = new ParkingLot(
        null,
        "Navita Parking",
        "14264523123",
        "Rua teste 123",
        "111111111",
        2,
        4,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    Mockito.when(repository.save(Mockito.any(ParkingLot.class))).thenReturn(parkingLot);
        
    ParkingLot savedParkingLot = service.addParkingLot(resquest);    
    
    assertThat(savedParkingLot).isNotNull();
    assertThat(savedParkingLot.getName()).isEqualTo(parkingLot.getName());
  }
  
  @Test
  @DisplayName("Throw error when update space is less than empty spaces")
  void shouldThrowErrorWhenUpdateSpaces() {
    RequestParkingLotDto resquest = new RequestParkingLotDto(
        "Navita Parking",
        "14564523103",
        "Rua teste 123",
        "111111111",
        2,
        4
    );
    
    ParkingLot parkingLot = new ParkingLot(
        null,
        "Navita Parking",
        "14264523123",
        "Rua teste 123",
        "111111111",
        2,
        4,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    Mockito.when(managerService.verifyIfCanUpdateSpaces(Mockito.any(ParkingLot.class), Mockito.any(RequestParkingLotDto.class)))
      .thenThrow(IllegalArgumentException.class);
    
    Mockito.when(repository.saveAndFlush(Mockito.any(ParkingLot.class))).thenReturn(parkingLot);
      
    try {
      ParkingLot savedParkingLot = service.addParkingLot(resquest);    
    }catch (Exception e) {
      assertTrue(e.getClass() == IllegalArgumentException.class);
    }
  }
  
  @Test
  @DisplayName("Throw error when delete with some space occupied")
  void shouldThrowErrorWhenDeleteSomeSpaceOccupied() {    
    ParkingLot parkingLot = new ParkingLot(
        null,
        "Navita Parking",
        "14264523123",
        "Rua teste 123",
        "111111111",
        2,
        4,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    Mockito.when(repository.findById(1l)).thenReturn(Optional.of(parkingLot));
    
    Mockito.when(managerService.verifyIfCanDeleteParkingLot(Mockito.any(ParkingLot.class)))
      .thenThrow(IllegalArgumentException.class);
    
    try {
     service.deleteById(1L);    
    }catch (Exception e) {
      assertTrue(e.getClass() == IllegalArgumentException.class);
    }
  }
  
}
