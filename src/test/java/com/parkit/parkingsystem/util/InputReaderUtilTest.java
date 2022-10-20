package com.parkit.parkingsystem.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InputReaderUtilTest {
    private InputReaderUtil inputReaderUtil;

    @BeforeEach
    public void setUp() {
        inputReaderUtil = new InputReaderUtil();

    }


    @Test
    void readSelectionTest() {
        InputStream in = new ByteArrayInputStream("7".getBytes());
        System.setIn(in);

        int input = inputReaderUtil.readSelection();

        assertEquals(7, input);
    }

    @Test
    void readSelectionErrorInputTest() {
        InputStream in = new ByteArrayInputStream("a".getBytes());
        System.setIn(in);

        int input = inputReaderUtil.readSelection();

        assertNotEquals(1, input);
    }

    @Test
    void readVehicleRegistrationNumber() throws Exception {
        InputStream in = new ByteArrayInputStream("AZERTY".getBytes());
        System.setIn(in);

        String test = inputReaderUtil.readVehicleRegistrationNumber();

        assertEquals("AZERTY", test);
    }


    @Test
    void readVehicleRegistrationNumber2() throws Exception {
        InputStream in = new ByteArrayInputStream(" ".getBytes());
        System.setIn(in);

        assertThrows(IllegalArgumentException.class, () -> inputReaderUtil.readVehicleRegistrationNumber());
    }
}

