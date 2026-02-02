
package tools;

public interface Acceptable {
    public final String HomeID_VALID = "^[hs][HS]\\d{4}$";
    public final String TourID_VALID = "^[tT]\\d{5}$";
    public final String BookID_VALID = "^[bB]\\d{5}$";
    
    public static boolean isValid(String data, String pattern){
        return data.matches(pattern);
    }
}
