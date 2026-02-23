
package tools;

public interface ValidationUtils {
    public final String HomeID_VALID = "(?i)^hs\\d{4}$";
    public final String TourID_VALID = "^[tT]\\d{5}$";
    public final String BookID_VALID = "^[bB]\\d{5}$";
    public final String INTEGER_VALID = "^-?\\d+$";
    public final String POSITIVE_INT_VALID = "^[1-9]\\d*$";
    public final String DOUBLE_VALID = "^-?\\d+(\\.\\d+)?$";
    public final String POSITIVE_DOUBLE_VALID = "^\\d+(\\.\\d+)?$";
    public final String TourName_VALID = "^[a-zA-Z0-9\\s-]{1,50}$";
    
    public static boolean isValid(String data, String pattern){
        return data.matches(pattern);
    }
}
