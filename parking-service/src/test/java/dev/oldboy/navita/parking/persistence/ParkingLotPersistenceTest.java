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
import dev.oldboy.navita.parking.parkinglot.repositories.ParkingLotRepository;

@DataJpaTest
@DisplayName("Parking Lot Persistence Tests")
public class ParkingLotPersistenceTest {
  
  @Autowired 
  private ParkingLotRepository repository;
  
  @BeforeEach
  void setUp() {
    ParkingLot parkingLot1 = new ParkingLot(
        null,
        "Navita Parking1",
        "14264523123",
        "Rua teste 123",
        "111111111",
        2,
        4,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    ParkingLot parkingLot2 = new ParkingLot(
        null,
        "Navita Parking2",
        "14264523124",
        "Rua teste 123",
        "111111111",
        2,
        4,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
      
    
    repository.save(parkingLot1);
    repository.save(parkingLot2);
    
    repository.flush();
  }
  
  @Test
  @DisplayName("Unique CNPJ")
  void shouldThrowExceptionWhenAddParkingLotWithRepetableCNPJ() {
    ParkingLot parkingLot1 = new ParkingLot(
        null,
        "Navita Parking1",
        "14264523123",
        "Rua teste 123",
        "111111111",
        2,
        4,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    try {
      ParkingLot savedParkingLot = repository.save(parkingLot1);
      repository.flush();   
    }catch(Exception e) {
      assertTrue(e.getCause() instanceof ConstraintViolationException);
    } 
  }
  
  @Test
  @DisplayName("Insert valid parking lot")
  void shouldReturnEntityWhenAddValidParkingLot() {
    ParkingLot parkingLot3 = new ParkingLot(
        null,
        "Navita Parking3",
        "14264503124",
        "Rua teste 123",
        "111111111",
        2,
        4,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    
    ParkingLot savedParkingLot = repository.save(parkingLot3);
    repository.flush();     
   
    assertNotNull(savedParkingLot.getId());
  }
  
  @Test
  @DisplayName("FindById")
  void shouldReturnParkingLotWhenValidId() {
    ParkingLot parkingLot4 = new ParkingLot(
        null,
        "Navita Parking4",
        "14264503224",
        "Rua teste 123",
        "111111111",
        2,
        4,
        LocalDateTime.now(),
        LocalDateTime.now()
    );
    
    ParkingLot savedParkingLot = repository.save(parkingLot4);
    repository.flush();   
    
    Optional<ParkingLot> foundParkingLot = repository.findById(savedParkingLot.getId());
    
    assertTrue(foundParkingLot.isPresent());
  }
  
  @Test
  @DisplayName("FindByCNPJ")
  void shouldReturnParkingLotWhenValidCNPJ() {  
    String cnpj = "14264523123";
    Optional<ParkingLot> foundParkingLot = repository.findByCnpj(cnpj);
    
    assertTrue(foundParkingLot.isPresent());
  }
  
  @Test
  @DisplayName("Delete parking lot by cnpj")
  void shouldDeleteEntityWhenValidCpf() {
    Long parkingLotCountBefore = repository.count();
    
    String cnpj = "14264523123"; 
    repository.deleteByCnpj(cnpj);
    
    Long parkingLotCountAfter = repository.count();
    
    assertThat(parkingLotCountAfter).isEqualTo(parkingLotCountBefore - 1); 
  }
  
}
