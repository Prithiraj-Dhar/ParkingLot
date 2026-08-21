package src.main.java.parkingLot.spot;

import src.main.java.parkingLot.vehicle.Vehicle;
import src.main.java.parkingLot.vehicle.VehicleSize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingManager_Deep_Dive {

    private final Map<VehicleSize, List<ParkingSpot>> availableSpots;
    private final Map<Vehicle, ParkingSpot> vehicleToSpotMap;
    private final Map<ParkingSpot, Vehicle> spotToVehicleMap;

    // Create Parking Manager based on a given map of available spots
    public ParkingManager_Deep_Dive(
            Map<VehicleSize, List<ParkingSpot>> availableSpots) {

        this.availableSpots = availableSpots;
        this.vehicleToSpotMap = new HashMap<>();
        this.spotToVehicleMap = new HashMap<>();
    }

    // Find a suitable parking spot for a vehicle
    public ParkingSpot findSpotForVehicle(Vehicle vehicle) {

        // Get the size of the actual vehicle
        VehicleSize vehicleSize = vehicle.getSize();

        // Start looking from the smallest spot
        // that can fit the vehicle
        for (VehicleSize size : VehicleSize.values()) {

            if (size.ordinal() >= vehicleSize.ordinal()) {

                List<ParkingSpot> spots = availableSpots.get(size);

                if (spots == null) {
                    continue;
                }

                for (ParkingSpot spot : spots) {

                    if (spot.isAvailable()) {
                        return spot;
                    }
                }
            }
        }

        return null;
    }

    // Park a vehicle
    public ParkingSpot parkVehicle(Vehicle vehicle) {

        ParkingSpot spot = findSpotForVehicle(vehicle);

        if (spot != null) {

            // Occupy the parking spot
            spot.occupy(vehicle);

            // Record bidirectional mapping
            vehicleToSpotMap.put(vehicle, spot);
            spotToVehicleMap.put(spot, vehicle);

            // Remove the spot from available spots
            List<ParkingSpot> spots = availableSpots.get(spot.getSize());

            if (spots != null) {
                spots.remove(spot);
            }

            return spot;
        }

        return null;
    }

    // Remove vehicle from parking lot
    public void unparkVehicle(Vehicle vehicle) {

        ParkingSpot spot = vehicleToSpotMap.remove(vehicle);

        if (spot != null) {

            // Remove reverse mapping
            spotToVehicleMap.remove(spot);

            // Vacate the spot
            spot.vacate();

            // Add the spot back to available spots
            List<ParkingSpot> spots = availableSpots.get(spot.getSize());

            if (spots != null) {
                spots.add(spot);
            }
        }
    }

    // Find vehicle's parking spot
    public ParkingSpot findVehicleBySpot(Vehicle vehicle) {
        return vehicleToSpotMap.get(vehicle);
    }

    // Find which vehicle is parked in a spot
    public Vehicle findSpotByVehicle(ParkingSpot spot) {
        return spotToVehicleMap.get(spot);
    }
}