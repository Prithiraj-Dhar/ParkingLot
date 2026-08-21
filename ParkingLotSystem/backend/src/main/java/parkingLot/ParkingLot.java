package src.main.java.parkingLot;

import src.main.java.parkingLot.fare.FareCalculator;
import src.main.java.parkingLot.spot.ParkingManager;
import src.main.java.parkingLot.spot.ParkingSpot;
import src.main.java.parkingLot.vehicle.Vehicle;


import java.math.BigDecimal;

import java.time.LocalDateTime;

public class ParkingLot {
    private final ParkingManager parkingManager; // Manages parking spots and vehicle assignments
    private final FareCalculator fareCalculator; // Calculates fare for parking sessions

    public ParkingLot(ParkingManager parkingManager, FareCalculator fareCalculator) {
        this.parkingManager = parkingManager;
        this.fareCalculator = fareCalculator;
    }

    // Method to handle vehicle entry into the parking lot
    public Ticket enterVehicle(Vehicle vehicle) {
        // Delegate parking logic to ParkingManager
        ParkingSpot spot = parkingManager.parkVehicle(vehicle);
        
        if (spot != null) {
            // Create ticket with entry time
            Ticket ticket = new Ticket(generateTicketId(), vehicle, spot, LocalDateTime.now());
            return ticket;
        } else {
            return null;  // No spot available
        }
    }

    // Method to handle vehicle exit from the parking lot using the main.parkinglot.fare.Ticket object
    public void leaveVehicle(Ticket ticket) {
        if (ticket != null && ticket.getExitTime() == null) {  // Ensure the ticket is valid and the vehicle hasn't already left
            // Set exit time
            ticket.setExitTime(LocalDateTime.now());
            
            // Delegate unparking logic to ParkingManager
            parkingManager.unparkVehicle(ticket.getVehicle());

            // Calculate the fare
            BigDecimal fare = fareCalculator.calculateFare(ticket);
        } else {
            // Invalid ticket or vehicle already exited. 
        }
    }

    // Helper method to generate a unique ticket ID
    private String generateTicketId() {
        return "TICKET-" + System.currentTimeMillis();
    }
}
