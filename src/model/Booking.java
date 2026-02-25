package model;

import java.util.Date;

public class Booking {

    private String bookingId;
    private String fullName;
    private String tourId;
    private Date bookingDate;
    private String phone;
    private double totalAmount;

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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        String bookingDateStr = bookingDate != null ? sdf.format(bookingDate) : "N/A";
        return String.format("%-12s | %-20s | %-10s | %-12s | %-12s | %-12.2f",
                bookingId, fullName, tourId, bookingDateStr, phone, totalAmount);
    }

}
