package com.spp.cp.db;

import com.spp.cp.domain.vehicle.Vehicle;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {

    Vehicle findVehicleByPlateNumber(String plateNumber);

}
