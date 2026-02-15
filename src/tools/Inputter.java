
package tools;

import java.util.Scanner;

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
}
