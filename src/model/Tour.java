package model;

import java.util.Date;

public class Tour {

    private String tourId;
    private String tourName;
    private String time;
    private double price;
    private String homeId;
    private Date departureDate;
    private Date endDate;
    private int tourist;

    public Tour() {
    }

    public Tour(String tourId, String tourName, String time, double price, String homeId, Date departureDate, Date endDate, int tourist) {
        this.tourId = tourId;
        this.tourName = tourName;
        this.time = time;
        this.price = price;
        this.homeId = homeId;
        this.departureDate = departureDate;
        this.endDate = endDate;
        this.tourist = tourist;
    }

    //getter and setter
    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getTourist() {
        return tourist;
    }

    public void setTourist(int tourist) {
        this.tourist = tourist;
    }

}
