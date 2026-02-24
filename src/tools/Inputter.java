
package tools;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import model.Booking;
import model.Tour;

public class Inputter {
    private Scanner ndl;

    public Inputter() {
        this.ndl = new Scanner(System.in);
    } 
    
    //Nhập dữ liệu kiểu chuỗi
    public String getString(String message){
        System.out.print(message);
        return ndl.nextLine();
    }
    
    //Nhập dữ liệu kiểu số nguyên
    public int getInt(String message){
        int kq = 0;
        String temp = getString(message);
        if(ValidationUtils.isValid(temp, ValidationUtils.INTEGER_VALID)){
            kq = Integer.parseInt(temp);
        }
        return kq;
    }

    //optional integer input - allows empty
    public int getIntOptional(String message){
        String temp = getString(message).trim();
        if (temp.isEmpty()) {
            return 0;
        }
        if(ValidationUtils.isValid(temp, ValidationUtils.INTEGER_VALID)){
            return Integer.parseInt(temp);
        }
        System.out.println("Invalid number!");
        return getIntOptional(message);
    }

    //Nhập dữ liệu kiểu số thực
    public double getDouble(String message){
        double kq = 0;
        String temp = getString(message);
        if(ValidationUtils.isValid(temp, ValidationUtils.DOUBLE_VALID)){
            kq = Double.parseDouble(temp);
        }
        return kq;
    }

    //optional double input - allows empty
    public double getDoubleOptional(String message){
        String temp = getString(message).trim();
        if (temp.isEmpty()) {
            return 0.0;
        }
        if(ValidationUtils.isValid(temp, ValidationUtils.DOUBLE_VALID)){
            return Double.parseDouble(temp);
        }
        System.out.println("Invalid number!");
        return getDoubleOptional(message);
    }
    
    //check data
    public String inputAndLoop(String message, String pattern, boolean isLoop){
        String result;
        boolean isInvalid;
        
        do{
            result = getString(message).trim();
            //Optional: cho phép bỏ trống
            if(!isLoop && result.isEmpty()){
                return result;
            }
            
            isInvalid = !ValidationUtils.isValid(result, pattern);
            
            if(isInvalid && isLoop){
                System.out.println("Data is invalid! Re-enter...");
            }
        }while(isLoop && isInvalid);
        return result;
    }

    //add tour
    public Tour inputTour(){
        Tour t = new Tour();
        t.setTourId(inputAndLoop("Enter tour ID: ", ValidationUtils.TourID_VALID, true));
        t.setTourName(inputAndLoop("Enter tour name: ", ValidationUtils.TourName_VALID, true));
        t.setPrice(getDouble("Enter price: "));
        t.setHomeId(inputAndLoop("Enter home ID: ", ValidationUtils.HomeID_VALID, true));
        t.setDepartureDate(getDate("Enter departure date (dd/MM/yyyy): "));
        t.setEndDate(getDate("Enter end date (dd/MM/yyyy): "));
        t.setTourist(getInt("Enter number of tourist: "));
        return t;
    }
    
    //update tour
    public Tour updateTour(){
        Tour t = new Tour();
        t.setTourName(inputAndLoop("Enter tour name: ", ValidationUtils.TourName_VALID, false));
        t.setPrice(getDoubleOptional("Enter price: "));
        t.setHomeId(inputAndLoop("Enter home ID: ", ValidationUtils.HomeID_VALID, false));
        t.setDepartureDate(getDateOptional("Enter departure date (dd/MM/yyyy): "));
        t.setEndDate(getDateOptional("Enter end date (dd/MM/yyyy): "));
        t.setTourist(getIntOptional("Enter number of tourist: "));
        return t;
    }
    
    //add booking
    public Booking inputBooking(){
        Booking b = new Booking();
        b.setBookingId(inputAndLoop("Enter booking ID: ", ValidationUtils.BookID_VALID, true));
        b.setFullName(inputAndLoop("Enter full name: ", ValidationUtils.FullName_VALID, true));
        b.setTourId(inputAndLoop("Enter tour ID: ", ValidationUtils.TourID_VALID, true));
        b.setBookingDate(getDate("Enter booking date (dd/MM/yyyy): "));
        b.setPhone(inputAndLoop("Enter phone number: ", ValidationUtils.Phone_VALID, true));
        b.setNumberOfPeople(getInt("Enter number of people: "));
        return b;
    }
    
    //update booking
    public Booking updateBooking(){
        Booking b = new Booking();
        b.setFullName(inputAndLoop("Enter full name: ", ValidationUtils.FullName_VALID, false));
        b.setTourId(inputAndLoop("Enter tour ID: ", ValidationUtils.TourID_VALID, false));
        b.setBookingDate(getDateOptional("Enter booking date (dd/MM/yyyy): "));
        b.setPhone(inputAndLoop("Enter phone number: ", ValidationUtils.Phone_VALID, false));
        b.setNumberOfPeople(getIntOptional("Enter number of people: "));
        return b;
    }
    
    //định dạng ngày, tháng, năm
    public Date getDate(String mess){
        String  dateStr = getString(mess);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            System.out.println("Invalid date format! Plese use dd/MM/yyyy");
            return getDate(mess);
        }
    }

    //optional date input - allows empty
    public Date getDateOptional(String mess){
        String dateStr = getString(mess).trim();
        if (dateStr.isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            System.out.println("Invalid date format! Please use dd/MM/yyyy");
            return getDateOptional(mess);
        }
    }
}
