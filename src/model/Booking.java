package model;

import java.util.Date;

public class Booking {

    private String bookingId;
    private String fullName;
    private String tourId;
    private Date bookingDate;
    private String phone;

    //constructor
    public Booking() {
    }

    public Booking(String bookingId, String fullName, String tourId, Date bookingDate, String phone) {
        this.bookingId = bookingId;
        this.fullName = fullName;
        this.tourId = tourId;
        this.bookingDate = bookingDate;
        this.phone = phone;
    }

    //getter and setter
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
