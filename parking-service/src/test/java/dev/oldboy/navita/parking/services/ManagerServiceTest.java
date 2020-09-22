package dev.oldboy.navita.parking.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import dev.oldboy.navita.parking.manager.repositories.ManagerRepository;
import dev.oldboy.navita.parking.manager.repositories.ManagerVehicleRepository;
import dev.oldboy.navita.parking.manager.services.ManagerServiceImpl;
import dev.oldboy.navita.parking.vehicle.services.VehicleService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Manager Service Tests")
public class ManagerServiceTest {
  
  @MockBean
  private ManagerRepository repository;
  
  @MockBean
  private ManagerVehicleRepository managerVehicleRepository;
  
  @MockBean
  private VehicleService vehicleService;
  
  @InjectMocks
  private ManagerServiceImpl service;
  
}
