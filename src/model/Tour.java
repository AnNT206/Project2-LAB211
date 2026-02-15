package model;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.text.SimpleDateFormat;
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
    private boolean booking;

    public Tour() {
    }

    public Tour(String tourId, String tourName, String time, double price, String homeId, Date departureDate, Date endDate, int tourist, boolean booking) {
        this.tourId = tourId;
        this.tourName = tourName;
        this.time = time;
        this.price = price;
        this.homeId = homeId;
        this.departureDate = departureDate;
        this.endDate = endDate;
        this.tourist = tourist;
        this.booking = booking;
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

    public boolean isBooking() {
        return booking;
    }

    public void setBooking(boolean booking) {
        this.booking = booking;
    }
    
    //calculate time
    public String Time(){
        long diff = endDate.getTime() - departureDate.getTime();
        long days = diff / (1000 * 60 * 60 * 24) + 1;
        return String.format("%d days %d night", days, days - 1);
    }

    @Override
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("%s,%s,%s,%.2f,%s,%s,%s,%d,%s", tourId, tourName, Time(), price, homeId, sdf.format(departureDate), sdf.format(endDate), tourist, booking ? TRUE : FALSE);
    }
}
