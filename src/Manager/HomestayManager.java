package Manager;

import java.io.File;
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
        this.pathFile = System.getProperty("user.dir") + File.separator + "Homestays.txt";
        readFromFile();
    }

    public boolean isSaved() {
        return saved;
    }

    //readFromFile
    public final void readFromFile() {
        homestayList.clear();

        List<String> lines = FileUtils.readLines(pathFile);
        System.out.println("Lines read: " + lines.size());

        for (String line : lines) {
            Homestay x = textToObject(line);
            if (x != null) {
                homestayList.add(x);
                System.out.println("Loaded homestay: " + x.getHomeID() + " - " + x.getHomeName());
            } else {
                System.out.println("Failed to parse line: " + line);
            }
        }
        System.out.println("Total homestays loaded: " + homestayList.size());
    }

    //textToObject
    public Homestay textToObject(String temp) {
        try {
            temp = temp.trim();
            if (temp.isEmpty()) {
                return null;
            }

            String[] part = temp.split("-");
            if (part.length < 5) {
                return null;
            }

            Homestay x = new Homestay();
            x.setHomeID(part[0].trim());
            x.setHomeName(part[1].trim());
            x.setRoomNumber(Integer.parseInt(part[2].trim()));

            // Build address from parts 3 to (n-1)
            StringBuilder address = new StringBuilder();
            for (int i = 3; i < part.length - 1; i++) {
                if (i > 3) {
                    address.append("-");
                }
                address.append(part[i].trim());
            }
            x.setAddress(address.toString());

            x.setMaximumcapacity(Integer.parseInt(part[part.length - 1].trim()));
            return x;

        } catch (Exception e) {
            return null;
        }
    }

    //findById
    public Homestay findById(String homeId) {
        for (Homestay homestay : homestayList) {
            if (homestay.getHomeID().equalsIgnoreCase(homeId)) {
                return homestay;
            }
        }
        return null;
    }
}
