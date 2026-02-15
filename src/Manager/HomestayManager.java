package Manager;

import java.util.ArrayList;
import java.util.List;
import model.Homestay;
import tools.FileUtils;

public class HomestayManager {

    private List<Homestay> homestayList;
    private String pathFile;
    private boolean saved;

    //constructor
    public HomestayManager() {
        homestayList = new ArrayList<>();
        this.saved = true;
        this.pathFile = "./Homestays.txt";
    }

    public boolean isSaved() {
        return saved;
    }

    //readFromFile
    public final void readFromFile() {
        homestayList.clear();

        List<String> lines = FileUtils.readLines(pathFile);
        for (String line : lines) {
            Homestay x = textToObject(line);
            if (x != null) {
                homestayList.add(x);
            }
        }
    }

    //textToObject
    public Homestay textToObject(String temp) {
        try {
            String[] part = temp.split("-", 5);
            if (part.length != 5) {
                return null;
            }
            Homestay x = new Homestay();
            x.setHomeID(part[0]);
            x.setHomeName(part[1]);
            x.setRoomNumber(Integer.parseInt(part[2]));
            x.setAddress(part[3]);
            x.setMaximumcapacity(Integer.parseInt(part[4]));
            return x;

        } catch (Exception e) {
            return null;
        }
    }
    
    //findById
    public Homestay findById(String homeId){
        for (Homestay homestay : homestayList) {
            if(homestay.getHomeID().equalsIgnoreCase(homeId)){
                return homestay;
            }
        }
        return null;
    }
}
