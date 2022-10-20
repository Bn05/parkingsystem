package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.model.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkingServiceTest {

    @InjectMocks
    private static ParkingService parkingService;

    @Mock
    private static InputReaderUtil inputReaderUtil;
    @Mock
    private static ParkingSpotDAO parkingSpotDAO;
    @Mock
    private static TicketDAO ticketDAO;

    @Mock
    private ParkingSpot parkingSpot;

    @BeforeEach
    public void setUpPerTest() {
        try {
            lenient().when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
            ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
            Ticket ticket = new Ticket();
            ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber("ABCDEF");
            lenient().when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
            lenient().when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(true);

            lenient().when(parkingSpotDAO.updateParking(any(ParkingSpot.class))).thenReturn(true);


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to set up test mock objects");
        }
    }

    @Test
    void processIncomingVehicleTestCarTest() {

        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingSpotDAO.getNextAvailableSlot(any())).thenReturn(1);
        when(ticketDAO.saveTicket(any())).thenReturn(true);
        when(ticketDAO.recurringUsers(any())).thenReturn(false);

        parkingService.processIncomingVehicle();

        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any());
        verify(ticketDAO, Mockito.times(1)).saveTicket(any());
        verify(ticketDAO, Mockito.times(1)).recurringUsers(any());
    }

    @Test
    void processIncomingVehicleTestCarTicketSaveFailTest() {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingSpotDAO.getNextAvailableSlot(any())).thenReturn(1);
        when(ticketDAO.saveTicket(any())).thenReturn(false);


        parkingService.processIncomingVehicle();

        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any());
        verify(ticketDAO, Mockito.times(1)).saveTicket(any());
        verify(ticketDAO, Mockito.times(0)).recurringUsers(any());
    }

    @Test
    void processIncomingVehicleTestCarRecurringUserTest() {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingSpotDAO.getNextAvailableSlot(any())).thenReturn(1);
        when(ticketDAO.saveTicket(any())).thenReturn(true);
        when(ticketDAO.recurringUsers(any())).thenReturn(true);

        parkingService.processIncomingVehicle();

        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any());
        verify(ticketDAO, Mockito.times(1)).saveTicket(any());
        verify(ticketDAO, Mockito.times(1)).recurringUsers(any());
    }

    @Test
    void processIncomingVehicleTestCarNoSlotAvailableTest() {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(parkingSpotDAO.getNextAvailableSlot(any())).thenReturn(0);

        parkingService.processIncomingVehicle();

        verify(parkingSpotDAO, Mockito.times(0)).updateParking(any());
        verify(ticketDAO, Mockito.times(0)).saveTicket(any());
        verify(ticketDAO, Mockito.times(0)).recurringUsers(any());
    }


    @Test
    void processIncomingVehicleTestCarIllegalArgumentTest() {
        when(inputReaderUtil.readSelection()).thenReturn(3);
        parkingService.processIncomingVehicle();

        verify(parkingSpotDAO, Mockito.times(0)).updateParking(any());
        verify(ticketDAO, Mockito.times(0)).saveTicket(any());
        verify(ticketDAO, Mockito.times(0)).recurringUsers(any());
    }

    @Test
    void processIncomingVehicleTestBikeTest() {
        when(inputReaderUtil.readSelection()).thenReturn(2);
        when(parkingSpotDAO.getNextAvailableSlot(any())).thenReturn(1);
        when(ticketDAO.saveTicket(any())).thenReturn(true);
        when(ticketDAO.recurringUsers(any())).thenReturn(false);

        parkingService.processIncomingVehicle();

        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any());
        verify(ticketDAO, Mockito.times(1)).saveTicket(any());
        verify(ticketDAO, Mockito.times(1)).recurringUsers(any());

    }

    @Test
    void processExitingVehicleTest() {
        parkingService.processExitingVehicle();
        verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
    }

    @Test
    void processExitingVehicleUpdateTicketFailTest() {
        when(ticketDAO.updateTicket(any())).thenReturn(false);

        parkingService.processExitingVehicle();

        verify(parkingSpotDAO, Mockito.times(0)).updateParking(any(ParkingSpot.class));
    }


}
