package com.parkit.parkingsystem.model.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    TicketDAO ticketDAO = new TicketDAO();

    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        double inHour = ticket.getInTime().getTime();
        double outHour = ticket.getOutTime().getTime();

        double duration = (outHour - inHour) / 1000 / 60 / 60; // différence de temps en millisecond, ("1000/60/60) permet de mettre cette valeur en heure.

        //Permet d'offrir la gratuité quand on reste moins de 30min.
        if (duration < 0.5) {
            duration = 0;
        }

        // Permet d'appliquer 5% de remise si client régulier.

        if (ticketDAO.recurringUsers(ticket.getVehicleRegNumber())) {
            duration = duration * 0.95;
        }

        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default:
                throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}