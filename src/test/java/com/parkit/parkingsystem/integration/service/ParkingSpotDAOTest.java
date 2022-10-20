package com.parkit.parkingsystem.integration.service;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.parkit.parkingsystem.constants.ParkingType.CAR;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


public class ParkingSpotDAOTest {

    public static ParkingSpotDAO parkingSpotDAO;

    public static DataBaseTestConfig dataBaseTestConfig;

    public static DataBasePrepareService dataBasePrepareService;

    @BeforeAll
    public static void setUp() {
        parkingSpotDAO = new ParkingSpotDAO();
        dataBaseTestConfig = new DataBaseTestConfig();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    public void setUpPerTest() {
        dataBasePrepareService.clearDataBaseEntries();
    }

    @Test
    void getNextAvailableSlotTest() {

        int test = parkingSpotDAO.getNextAvailableSlot(CAR);

        assertEquals(1, test);
    }

    @Test
    void updateParkingTest(){
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);

        boolean test = parkingSpotDAO.updateParking(parkingSpot);

        assertTrue(test);

    }
}
