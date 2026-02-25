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

        for (String line : lines) {
            Homestay x = textToObject(line);
            if (x != null) {
                homestayList.add(x);
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

            //duyệt mảng phần tử ở index thứ 3
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

    //statistics on total number of tourists who have booked each homestay
    public void statisticsTouristsByHomestay(TourManager tm, BookingManager bm) {
        System.out.println("======= HOMESTAY BOOKING STATISTICS =======");
        System.out.printf("%-10s | %-25s | %-15s | %-15s | %-15s%n",
                "Home ID", "Home Name", "Max Capacity", "Booked Tours", "Total Tourists");
        System.out.println("-------------------------------------------------------------------------");

        int grandTotalTourists = 0;

        for (Homestay homestay : homestayList) {
            int bookedTourists = 0;
            int bookedTours = 0;

            // Find all tours for this homestay and sum up the tourists
            for (model.Tour tour : tm.getTourList()) {
                if (tour.getHomeId().equalsIgnoreCase(homestay.getHomeID())) {
                    // Check if this tour has bookings
                    boolean hasBooking = false;
                    for (model.Booking booking : bm.getBookingList()) {
                        if (booking.getTourId().equalsIgnoreCase(tour.getTourId())) {
                            hasBooking = true;
                            break;
                        }
                    }

                    if (hasBooking) {
                        bookedTours++;
                        bookedTourists += tour.getTourist();
                    }
                }
            }

            grandTotalTourists += bookedTourists;

            System.out.printf("%-10s | %-25s | %-15d | %-15d | %-15d%n",
                    homestay.getHomeID(),
                    homestay.getHomeName(),
                    homestay.getMaximumcapacity(),
                    bookedTours,
                    bookedTourists);
        }

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Total tourists who have booked homestays: " + grandTotalTourists);
        System.out.println("===========================================");
    }
}
