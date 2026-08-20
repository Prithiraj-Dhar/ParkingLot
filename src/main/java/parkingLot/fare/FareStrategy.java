package main.java.parkingLot.fare;

import main.java.parkingLot.Ticket;

import java.math.BigDecimal;

public interface FareStrategy {

    BigDecimal calculateFare(Ticket ticket, BigDecimal inputFare);
}