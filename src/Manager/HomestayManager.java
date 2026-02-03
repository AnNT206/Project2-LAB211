package Manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Homestay;

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
        try {
            FileReader fr = null;
            
            File f = new File(this.pathFile);
            if (!f.exists()) {
                System.out.println("Homestays.txt not found");
                return;
            }
            
            fr = new FileReader(f);
            
            BufferedReader br = new BufferedReader(fr);
            
            String temp = "";
            while ((temp = br.readLine()) != null) {
                Homestay x = textToObject(temp);
                if (x != null) {
                    homestayList.add(x);
                }
            }
            
            fr.close();
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HomestayManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HomestayManager.class.getName()).log(Level.SEVERE, null, ex);
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
}
