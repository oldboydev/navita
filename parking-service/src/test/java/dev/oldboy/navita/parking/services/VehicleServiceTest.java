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

import dev.oldboy.navita.parking.vehicle.dtos.RequestVehicleDto;
import dev.oldboy.navita.parking.vehicle.exceptions.VehicleNotFoundException;
import dev.oldboy.navita.parking.vehicle.models.Vehicle;
import dev.oldboy.navita.parking.vehicle.repositories.VehiclesRepository;
import dev.oldboy.navita.parking.vehicle.services.VehicleServiceImpl;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Vehicles Service Tests")
public class VehicleServiceTest {
  
  @MockBean
  private VehiclesRepository repository;
  
  @InjectMocks
  private VehicleServiceImpl service;
  
  @Test
  @DisplayName("Find vehicle by id")
  void shouldReturnVehicleWhenPassAValidId() {
    Long id = 1L;
    
    Mockito.when(repository.findById(id))
      .thenReturn(Optional.of(
          new Vehicle(
              null,
              "Fiat",
              "Uno",
              "Preta",
              "ABC123456",
              2,
              LocalDateTime.now(),
              LocalDateTime.now()
          )));
    
    Vehicle vehicle1 = service.findById(id);
    
    assertThat(vehicle1).isNotNull();
    assertThat(vehicle1.getBrand()).isEqualTo("Fiat");
  }
  
  @Test
  @DisplayName("Find vehicle by Plate")
  void shouldReturnVehicleWhenPassAValidCnpj() {
    String plate = "ABC123456";
    
    Mockito.when(repository.findByPlate(plate))
    .thenReturn(Optional.of(
        new Vehicle(
            null,
            "Fiat",
            "Uno",
            "Preta",
            "ABC123456",
            2,
            LocalDateTime.now(),
            LocalDateTime.now()
        )));
  
    Vehicle vehicle1 = service.findByPlate(plate);
    
    assertThat(vehicle1).isNotNull();
    assertThat(vehicle1.getBrand()).isEqualTo("Fiat");
  }
  
  @Test
  @DisplayName("Throw error when not Find vehicle by Id")
  void shouldThrowErroWhenNotFindVehicle() {
    Long id = 1L;
    
    Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
    
    try {
      Vehicle Vehicle1 = service.findById(id);;
    }catch(Exception e) {
      assertTrue(e.getClass() == VehicleNotFoundException.class);
    }
  }
  
  @Test
  @DisplayName("Save vehicle")
  void shouldReturnVehicleWhenSave() {
    RequestVehicleDto resquest = new RequestVehicleDto(
        "Fiat",
        "Uno",
        "Preta",
        "ABC123456",
        2
    );
    
    Vehicle Vehicle = new Vehicle(
        null,
        "Fiat",
        "Uno",
        "Preta",
        "ABC123456",
        2,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    Mockito.when(repository.save(Mockito.any(Vehicle.class))).thenReturn(Vehicle);
        
    Vehicle savedVehicle = service.addVehicle(resquest);    
    
    assertThat(savedVehicle).isNotNull();
    assertThat(savedVehicle.getBrand()).isEqualTo("Fiat");
  }
}
