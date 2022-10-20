package com.parkit.parkingsystem.integration.service;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static junit.framework.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TicketDAOTest {

    public static TicketDAO ticketDAO;
    public static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();

    public static DataBasePrepareService dataBasePrepareService;


    @BeforeAll
    public static void setUp() {
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    public void setUpPerTest() {
        dataBasePrepareService.clearDataBaseEntries();
    }


    @Test
    void saveTicketTest() {
        Ticket ticket = new Ticket();
        ticket.setParkingSpot(new ParkingSpot(1, ParkingType.CAR, false));
        ticket.setId(1);
        ticket.setVehicleRegNumber("ABCDE");
        ticket.setPrice(2);
        ticket.setInTime(new Date());
        ticket.setOutTime(new Date());

        boolean test = ticketDAO.saveTicket(ticket);

        assertFalse(test);

        Ticket ticketSave = ticketDAO.getTicket("ABCDE");

        assertEquals(ParkingType.CAR, ticketSave.getParkingSpot().getParkingType());
        assertEquals(1, ticketSave.getParkingSpot().getId());
        assertEquals("ABCDE", ticketSave.getVehicleRegNumber());
        assertEquals(2.0, ticketSave.getPrice());
        assertNotNull(ticketSave.getInTime());
        assertNotNull(ticketSave.getOutTime());
    }

    @Test
    void saveTicketNull() {
        Ticket ticket = new Ticket();

        boolean test = ticketDAO.saveTicket(ticket);

        assertFalse(test);

        Ticket ticketSave = ticketDAO.getTicket("ABCDE");

        assertNull(ticketSave);
    }

    @Test
    void getTicketvehicleRegNumberNullTest() {

        Ticket test = ticketDAO.getTicket("ABCDEF");

        assertNull(test);
    }

    @Test
    void updateTicketTest() {
        Ticket ticket = new Ticket();
        ticket.setParkingSpot(new ParkingSpot(1, ParkingType.CAR, false));
        ticket.setId(1);
        ticket.setPrice(2);
        ticket.setInTime(new Date());
        ticket.setOutTime(new Date());

        boolean test = ticketDAO.updateTicket(ticket);

        assertTrue(test);
    }

    @Test
    void updateTicketNullTest() {
        Ticket ticket = new Ticket();

        boolean test = ticketDAO.updateTicket(ticket);

        assertFalse(test);
    }

}



