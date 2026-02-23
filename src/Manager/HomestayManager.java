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
            // Replace multiple spaces with single space to normalize
            temp = temp.replaceAll("\\s+", " ").trim();

            String[] part = temp.split("-");
            if (part.length < 4) {
                return null;
            }

            Homestay x = new Homestay();
            x.setHomeID(part[0].trim());

            // Extract room number from home name (last number before the first hyphen)
            String homeNameWithRoom = part[1].trim();
            String[] nameParts = homeNameWithRoom.split("\\s+");
            int roomNumber = -1;
            StringBuilder homeName = new StringBuilder();

            // Find the last numeric part in the home name as room number
            for (int i = nameParts.length - 1; i >= 0; i--) {
                if (nameParts[i].matches("\\d+")) {
                    roomNumber = Integer.parseInt(nameParts[i]);
                    // Build home name without the room number
                    for (int j = 0; j < i; j++) {
                        homeName.append(nameParts[j]);
                        if (j < i - 1) {
                            homeName.append(" ");
                        }
                    }
                    break;
                }
            }

            if (roomNumber == -1) {
                // No room number found in home name, use the next part as room number
                x.setHomeName(homeNameWithRoom);
                x.setRoomNumber(Integer.parseInt(part[2].trim()));
                // Address is parts 3 to (n-1)
                StringBuilder address = new StringBuilder();
                for (int i = 3; i < part.length - 1; i++) {
                    if (i > 3) {
                        address.append("-");
                    }
                    address.append(part[i].trim());
                }
                x.setAddress(address.toString());
            } else {
                // Room number found in home name
                x.setHomeName(homeName.toString());
                x.setRoomNumber(roomNumber);
                // Address is parts 2 to (n-1) (since room number was in home name)
                StringBuilder address = new StringBuilder();
                for (int i = 2; i < part.length - 1; i++) {
                    if (i > 2) {
                        address.append("-");
                    }
                    address.append(part[i].trim());
                }
                x.setAddress(address.toString());
            }

            // Last part is the maximum capacity
            x.setMaximumcapacity(Integer.parseInt(part[part.length - 1].trim()));
            return x;

        } catch (Exception e) {
            System.out.println("Error parsing line: " + e.getMessage());
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
