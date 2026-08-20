package main.java.parkingLot.spot;

import main.java.parkingLot.vehicle.Vehicle;
import main.java.parkingLot.vehicle.VehicleSize;

public interface ParkingSpot {

    boolean isAvailable();

    void occupy(Vehicle vehicle);

    void vacate();

    int getSpotNumber();

    VehicleSize getSize();
}