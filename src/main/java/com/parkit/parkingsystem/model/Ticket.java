package com.parkit.parkingsystem.model;

import java.util.Date;

public class Ticket {
    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private Date inTime;
    private Date outTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        if (parkingSpot == null) {
            return null;
        }
        return new ParkingSpot(parkingSpot);
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        if (parkingSpot == null) {
            this.parkingSpot = null;
        } else {
            this.parkingSpot = new ParkingSpot(parkingSpot);
        }
    }


    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getInTime() {
        if (this.inTime == null) {
            return null;
        }
        return new Date(inTime.getTime());
    }

    public void setInTime(Date inTime) {
        if (inTime == null) {
            this.inTime = null;
        } else {
            this.inTime = new Date(inTime.getTime());
        }
    }

    public Date getOutTime() {
        if (this.outTime == null) {
            return null;
        }
        return new Date(outTime.getTime());
    }

    public void setOutTime(Date outTime) {
        if (outTime == null) {
            this.outTime = null;
        } else {
            this.outTime = new Date(outTime.getTime());
        }
    }
}
