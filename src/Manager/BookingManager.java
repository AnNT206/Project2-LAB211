package Manager;

import java.text.SimpleDateFormat;
import java.util.Date;
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

        //test
        Booking b = new Booking();
        b.setBookingId("b00011");
        b.setFullName("John Doe");
        b.setTourId("T00011");
        b.setBookingDate(new Date());
        b.setPhone("0909090909");
        b.setNumberOfPeople(2);
        b.setTotalAmount(200.0);
        bookingList.add(b);
        saved = false;
        System.out.println("Booking added: " + b.getBookingId());
        System.out.println("Booking: " + b.toString());
    }

    //readFromFile
    public final void readFromFile() {
        bookingList.clear();
        List<String> lines = FileUtils.readLines(pathFile);
        System.out.println("Lines read: " + lines.size());

        for (String line : lines) {
            Booking b = textToObject(line);
            if (b != null) {
                bookingList.add(b);
                System.out.println("Loaded booking: " + b.getBookingId() + " - " + b.getFullName());
            } else {
                System.out.println("Failed to parse line: " + line);
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

            // Handle optional fields (numberOfPeople, totalAmount)
            if (part.length >= 7) {
                b.setNumberOfPeople(Integer.parseInt(part[5]));
                b.setTotalAmount(Double.parseDouble(part[6]));
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
            System.out.println("Tour does not exist!");
            return false;
        }

        // Get the homestay to check capacity
        Homestay homestay = hm.findById(tour.getHomeId());
        if (homestay == null) {
            System.out.println("Homestay does not exist!");
            return false;
        }

        int remaining = homestay.getMaximumcapacity() - tour.getTourist();
        if (newBooking.getNumberOfPeople() > remaining) {
            System.out.println("Exceeds homestay capacity!");
            return false;
        }

        double total = tour.getPrice() * newBooking.getNumberOfPeople();
        newBooking.setTotalAmount(total);
        bookingList.add(newBooking);
        tour.setTourist(tour.getTourist() + newBooking.getNumberOfPeople());
        tour.setBooking(true);
        saved = false;
        System.out.println("Add booking successfully");
        return true;
    }

    //update
    public boolean update(String bookingID, Booking updateBooking, HomestayManager hm, TourManager tm) {
        Booking existing = findById(bookingID);
        if (existing == null) {
            System.out.println("Booking ID does not exist");
            return false;
        }

        // Check if the new tour exists only if tourId is being updated (not empty)
        Tour tour = null;
        if (updateBooking.getTourId() != null && !updateBooking.getTourId().trim().isEmpty()) {
            tour = tm.findById(updateBooking.getTourId());
            if (tour == null) {
                System.out.println("Tour ID does not exist");
                return false;
            }
        }

        // Check if homestay exists for the tour only if tour is being updated
        if (tour != null && hm.findById(tour.getHomeId()) == null) {
            System.out.println("Homestay ID does not exist");
            return false;
        }

        // Validate capacity only if numberOfPeople is being updated (greater than 0)
        if (updateBooking.getNumberOfPeople() > 0 && tour != null) {
            Homestay homestay = hm.findById(tour.getHomeId());
            if (homestay != null) {
                int remaining = homestay.getMaximumcapacity() - tour.getTourist();
                if (updateBooking.getNumberOfPeople() > remaining) {
                    System.out.println("Exceeds homestay capacity!");
                    return false;
                }
            }
        }

        // Update fields only if they are not empty/null/zero
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

        if (updateBooking.getNumberOfPeople() > 0) {
            existing.setNumberOfPeople(updateBooking.getNumberOfPeople());
            // Recalculate total amount if numberOfPeople changed
            if (tour != null) {
                double total = tour.getPrice() * updateBooking.getNumberOfPeople();
                existing.setTotalAmount(total);
            }
        }

        if (updateBooking.getTotalAmount() != 0) {
            existing.setTotalAmount(updateBooking.getTotalAmount());
        }

        saved = false;
        System.out.println("Update booking successfully");
        return true;
    }

    //remove
    public boolean remove(String bookingId) {
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
    public void listByFullName(String fullName) {
        System.out.println("Booking List for '" + fullName + "':");
        System.out.println("--------------------------------");
        System.out.printf("%-12s | %-20s | %-10s | %-12s | %-12s | %-14s | %-12s%n", "Booking ID", "Full Name", "Tour ID", "Booking Date", "Phone", "NumberOfPeople", "TotalAmount");
        
        System.out.println("--------------------------------");

        boolean found = false;
        for (Booking booking : bookingList) {
            if (booking.getFullName().toLowerCase().contains(fullName.toLowerCase())) {
                System.out.println(booking.toString());
                found = true;
            }
        }


        if (!found) {
            System.out.println("No bookings found with name containing: " + fullName);
        }
        System.out.println("--------------------------------");
    }

    //statistics total number of tourists
    public void statisticsTotalTourists() {
        int totalTourists = 0;

        for (Booking booking : bookingList) {
            totalTourists += booking.getNumberOfPeople();
        }

        System.out.println("======= TOURIST STATISTICS =======");
        System.out.println("Total number of bookings: " + bookingList.size());
        System.out.println("Total number of tourists: " + totalTourists);
        System.out.println("==================================");
    }
}
