package Manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Booking;
import model.Homestay;
import model.Tour;
import tools.FileUtils;

public class BookingManager {

    private List<Booking> bookingList;
    private String pathFile;
    private boolean saved;

    //constructor
    public BookingManager() {
        bookingList = new ArrayList<>();
        this.saved = true;
        this.pathFile = "./Bookings.txt";
        readFromFile();
    }

    //readFromFile
    public final void readFromFile() {
        bookingList.clear();
        List<String> lines = FileUtils.readLines(pathFile);

        for (String line : lines) {
            Booking b = textToObject(line);
            if (b != null) {
                bookingList.add(b);
            }
        }
        System.out.println("Total bookings loaded: " + bookingList.size());
    }

    //findById
    public Booking findById(String bookingId) {
        for (Booking booking : bookingList) {
            if (booking.getBookingId().equalsIgnoreCase(bookingId)) {
                return booking;
            }
        }
        return null;
    }

    //textToObject
    public Booking textToObject(String temp) {
        try {
            String[] part = temp.split(",");
            if (part.length < 5) {
                System.out.println("Error: Expected at least 5 parts, got " + part.length + " for line: " + temp);
                return null;
            }
            for (int i = 0; i < part.length; i++) {
                part[i] = part[i].trim();
            }
            Booking b = new Booking();
            b.setBookingId(part[0]);
            b.setFullName(part[1]);
            b.setTourId(part[2]);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            b.setBookingDate(sdf.parse(part[3]));
            b.setPhone(part[4]);

            // Handle optional field (totalAmount)
            if (part.length >= 6) {
                b.setTotalAmount(Double.parseDouble(part[5]));
            }
            return b;
        } catch (Exception e) {
            System.out.println("Error parsing line: " + e.getMessage());
            return null;
        }
    }

    //addNew
    public boolean addNewBooking(HomestayManager hm, TourManager tm, Booking newBooking) {
        if (findById(newBooking.getBookingId()) != null) {
            System.out.println("Booking ID already exists!");
            return false;
        }

        Tour tour = tm.findById(newBooking.getTourId());
        if (tour == null) {
            System.out.println("Tour does not exist");
            return false;
        }

        Homestay homestay = hm.findById(tour.getHomeId());
        if (homestay == null) {
            System.out.println("Homestay does not exist");
            return false;
        }

        bookingList.add(newBooking);
        tour.setBooking(true);
        saved = false;
        System.out.println("Add booking successfully");
        return true;
    }

    //update
    public boolean updateBooking(String bookingId, HomestayManager hm, TourManager tm, Booking updateBooking) {
        Booking existing = findById(bookingId);
        if (existing == null) {
            System.out.println("Booking ID does not exist");
            return false;
        }

        Tour tour = null;
        String tourIdToUse = existing.getTourId();

        if (updateBooking.getTourId() != null && !updateBooking.getTourId().trim().isEmpty()) {
            tourIdToUse = updateBooking.getTourId();
            tour = tm.findById(tourIdToUse);
            if (tour == null) {
                System.out.println("Tour ID does not exist");
            }
        } else {
            tour = tm.findById(existing.getTourId());
        }
        
        if(tour != null && hm.findById(tour.getHomeId()) == null){
            System.out.println("Home ID does not exist");
            return false;
        }
        
        if (updateBooking.getFullName() != null && !updateBooking.getFullName().trim().isEmpty()) {
            existing.setFullName(updateBooking.getFullName());
        }
        
        if (updateBooking.getTourId() != null && !updateBooking.getTourId().trim().isEmpty()) {
            existing.setTourId(updateBooking.getTourId());
        }
        
        if (updateBooking.getBookingDate() != null) {
            existing.setBookingDate(updateBooking.getBookingDate());
        }
        
        if (updateBooking.getPhone() != null && !updateBooking.getPhone().trim().isEmpty()) {
            existing.setPhone(updateBooking.getPhone());
        }
        
        saved = false;
        System.out.println("Update booking successfully");
        return true;
    }

    //remove
    public boolean removeBooking(String bookingId) {
        if (findById(bookingId) == null) {
            System.out.println("Booking ID does not exist");
            return false;
        }
        bookingList.remove(findById(bookingId));
        saved = false;
        System.out.println("Remove booking successfully");
        return true;
    }

    //getBookingList
    public List<Booking> getBookingList() {
        return bookingList;
    }

    //listByFullName
    public void listByFullName(String fullName, TourManager tm) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Booking List for '" + fullName + "':");
        System.out.println("--------------------------------");
        System.out.printf("%-12s | %-20s | %-10s | %-12s | %-12s | %-12s%n", "Booking ID", "Full Name", "Tour ID", "Booking Date", "Phone", "TotalAmount");
        System.out.println("--------------------------------");

        boolean found = false;
        for (Booking booking : bookingList) {
            if (booking.getFullName().toLowerCase().contains(fullName.toLowerCase())) {
                Tour tour = tm.findById(booking.getTourId());
                double totalAmount = 0.0;
                if (tour != null) {
                    totalAmount = tour.getTourist() * tour.getPrice();
                }

                String bookingDateStr = booking.getBookingDate() != null ? sdf.format(booking.getBookingDate()) : "N/A";
                System.out.printf("%-12s | %-20s | %-10s | %-12s | %-12s | %-12.2f%n",
                        booking.getBookingId(),
                        booking.getFullName(),
                        booking.getTourId(),
                        bookingDateStr,
                        booking.getPhone(),
                        totalAmount);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No bookings found with name containing: " + fullName);
        }
        System.out.println("--------------------------------");
    }

    //statistics total number of tourists
    public void statisticsTotalTourists(TourManager tm) {
        int totalTourists = 0;

        for (Booking booking : bookingList) {
            Tour tour = tm.findById(booking.getTourId());
            if (tour != null) {
                totalTourists += tour.getTourist();
            }
        }

        System.out.println("======= TOURIST STATISTICS =======");
        System.out.println("Total number of bookings: " + bookingList.size());
        System.out.println("Total number of tourists: " + totalTourists);
        System.out.println("==================================");
    }
}
