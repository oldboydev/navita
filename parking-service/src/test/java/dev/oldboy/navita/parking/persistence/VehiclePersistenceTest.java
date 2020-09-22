package dev.oldboy.navita.parking.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import dev.oldboy.navita.parking.parkinglot.models.ParkingLot;
import dev.oldboy.navita.parking.vehicle.models.Vehicle;
import dev.oldboy.navita.parking.vehicle.repositories.VehiclesRepository;

@DataJpaTest
@DisplayName("Vehicle Persistence Tests")
public class VehiclePersistenceTest {
  
  @Autowired
  private VehiclesRepository repository;
  
  @BeforeEach
  void setUp() {
    Vehicle vehicle1 = new Vehicle(
        null,
        "Fiat",
        "Uno",
        "Preta",
        "ABC123456",
        2,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    Vehicle vehicle2 = new Vehicle(
        null,
        "Chevrolet",
        "Onix",
        "Prata",
        "DEF78910",
        2,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    Vehicle moto1 = new Vehicle(
        null,
        "Honda",
        "CG",
        "Prata",
        "0123456ABC",
        1,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    Vehicle moto2 = new Vehicle(
        null,
        "Yamaha",
        "Fazer 150cc",
        "Azul",
        "ZXY012345",
        1,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    repository.save(vehicle1);
    repository.save(vehicle2);
    repository.save(moto1);
    repository.save(moto2);
    
    repository.flush();
  }
  
  @Test
  @DisplayName("Unique Plate")
  void shouldThrowExceptionWhenAddVehicleWithRepetablePlate() {
    Vehicle vehicle1 = new Vehicle(
        null,
        "Fiat",
        "Uno",
        "Preta",
        "ABC123456",
        2,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    try {
      Vehicle savedVehicle = repository.save(vehicle1);
      repository.flush();   
    }catch(Exception e) {
      assertTrue(e.getCause() instanceof ConstraintViolationException);
    } 
  }
  
  @Test
  @DisplayName("Insert valid vehicle")
  void shouldReturnEntityWhenAddValidVehicle() {
    Vehicle vehicle3 = new Vehicle(
        null,
        "Fiat",
        "Uno",
        "Preta",
        "ABC123102",
        2,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
        
    Vehicle savedVehicle = repository.save(vehicle3);
    repository.flush();      
   
    assertNotNull(savedVehicle.getId());
  }
  
  @Test
  @DisplayName("FindById")
  void shouldReturnVehicleWhenValidId() {
    Vehicle vehicle4 = new Vehicle(
        null,
        "Fiat",
        "Uno",
        "Preta",
        "ZABC123102",
        2,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
        
    Vehicle savedVehicle = repository.save(vehicle4);
    repository.flush();   
    
    Optional<Vehicle> foundVehicle = repository.findById(savedVehicle.getId());
    
    assertTrue(foundVehicle.isPresent());
  }
  
  @Test
  @DisplayName("FindByPlate")
  void shouldReturnVehicleLotWhenValidPlate() {  
    String plate = "ABC123456";
    Optional<Vehicle> foundVehicle = repository.findByPlate(plate);
    
    assertTrue(foundVehicle.isPresent());
  }
  
  @Test
  @DisplayName("Delete vehicle by plate")
  void shouldDeleteEntityWhenValidPlate() {
    Long vehicleCountBefore = repository.count();
    
    String plate = "ABC123456"; 
    repository.deleteByPlate(plate);
    
    Long vehicleCountAfter = repository.count();
    
    assertThat(vehicleCountAfter).isEqualTo(vehicleCountBefore - 1); 
  }
}
