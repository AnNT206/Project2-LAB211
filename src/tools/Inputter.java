
package tools;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import model.Tour;

public class Inputter {
    private Scanner ndl;

    public Inputter() {
        this.ndl = new Scanner(System.in);
    } 
    
    //Nhập dữ liệu kiểu chuỗi
    public String getString(String message){
        System.out.println(message);
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
    
    //Nhập dữ liệu kiểu số thực
    public double getDouble(String message){
        double kq = 0;
        String temp = getString(message);
        if(ValidationUtils.isValid(temp, ValidationUtils.DOUBLE_VALID)){
            kq = Double.parseDouble(temp);
        }
        return kq;
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
        t.setTourName(inputAndLoop("Enter tour name: ", ValidationUtils.TourName_VALID, true));
        t.setTime(inputAndLoop("Enter time: ", ValidationUtils.TIME_VALID, true));
        t.setPrice(getDouble("Enter price: "));
        t.setHomeId(inputAndLoop("Enter home ID: ", ValidationUtils.HomeID_VALID, true));
        t.setDepartureDate(getDate("Enter departure date (dd/MM/yyyy): "));
        t.setEndDate(getDate("Enter end date (dd/MM/yyyy): "));
        t.setTourist(getInt("Enter number of tourist: "));
        return t;
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
}
