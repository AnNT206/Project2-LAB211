
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
            
            isInvalid = !Acceptable.isValid(result, pattern);
            
            if(isInvalid && isLoop){
                System.out.println("Data is invalid! Re-enter...");
            }
        }while(isLoop && isInvalid);
        return result;
    }
}
