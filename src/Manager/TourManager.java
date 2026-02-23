package Manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Booking;
import model.Homestay;
import model.Tour;
import tools.FileUtils;

public class TourManager {

    private List<Tour> tourList;
    private String pathFile;
    private boolean saved;

    //constructor
    public TourManager() {
        tourList = new ArrayList<>();
        this.saved = true;
        this.pathFile = "./Tours.txt";
        readFromFile();

        //test
        Tour t = new Tour();
        t.setTourId("T00011");
        t.setTourName("Ha Long Bay Tour");
        t.setPrice(100.0);
        t.setHomeId("hs0001");
        t.setDepartureDate(new Date());
        t.setEndDate(new Date());
        t.setTourist(2);
        t.setBooking(false);
        tourList.add(t);
        saved = false;
        System.out.println("Tour added: " + t.getTourId());
        System.out.println("Tour: " + t.toString());
    }

    public Tour findById(String TourId) {
        for (Tour tour : tourList) {
            if (tour.getTourId().equalsIgnoreCase(TourId)) {
                return tour;
            }
        }
        return null;
    }

    //addNew
    public boolean addNew(HomestayManager hm, Tour t) {
        //check isDuplicate
        if (findById(t.getTourId()) != null) {
            System.out.println("Tour ID already exists");
            return false;
        }

        //check homestay exist or not
        Homestay home = hm.findById(t.getHomeId());
        if (home == null) {
            System.out.println("Home ID does not exist");
            return false;
        }

        //check departure date
        if (!t.getEndDate().after(t.getDepartureDate())) {
            System.out.println("End date must be after departure date");
            return false;
        }

        //check customer
        if (t.getTourist() <= 0) {
            System.out.println("Number of tourist must be greater than 0");
            return false;
        }

        //check capacity
        if (t.getTourist() > home.getMaximumcapacity()) {
            System.out.println("Number of tourist exceeds maximum capacity of homestay");
        }

        //booking = false
        t.setBooking(false);

        //add new
        tourList.add(t);
        saved = false;
        System.out.println("Add new tour successfully");
        return true;
    }

    //Update tour
    public boolean updateTour(String tourId, HomestayManager hm, Tour updatedTour) {
        Tour existing = findById(tourId);
        if (existing == null) {
            System.out.println("Tour not found");
            return false;
        }

        if (hm.findById(updatedTour.getHomeId()) == null) {
            System.out.println("Home ID does not exitst");
            return false;
        }

        if (!updatedTour.getEndDate().after(updatedTour.getDepartureDate())) {
            System.out.println("End date must be after departure date");
            return false;
        }

        if (updatedTour.getTourist() <= 0) {
            System.out.println("Number of tourist must be greater than 0");
            return false;
        }

        //update new information
        existing.setTourName(updatedTour.getTourName());
        existing.setPrice(updatedTour.getPrice());
        existing.setHomeId(updatedTour.getHomeId());
        existing.setDepartureDate(updatedTour.getDepartureDate());
        existing.setEndDate(updatedTour.getEndDate());
        existing.setTourist(updatedTour.getTourist());

        saved = false;
        System.out.println("Update tour successfully");
        return true;
    }


    //List tours with departure dates earlier than current date
    public void listPastTours() {
        java.util.Date currentDate = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        System.out.println("======= TOURS WITH PAST DEPARTURE DATES =======");
        System.out.println("Current Date: " + sdf.format(currentDate));
        System.out.println("-------------------------------------------------");
        System.out.printf("%-10s | %-25s | %-12s | %-12s | %-10s | %-8s%n",
                "Tour ID", "Tour Name", "Departure", "End Date", "Tourists", "Booking");
        System.out.println("-------------------------------------------------");

        int count = 0;
        for (Tour tour : tourList) {
            if (tour.getDepartureDate().before(currentDate)) {
                System.out.printf("%-10s | %-25s | %-12s | %-12s | %-10d | %-8s%n",
                        tour.getTourId(),
                        tour.getTourName(),
                        sdf.format(tour.getDepartureDate()),
                        sdf.format(tour.getEndDate()),
                        tour.getTourist(),
                        tour.isBooking() ? "True" : "False");
                count++;
            }
        }

        System.out.println("-------------------------------------------------");
        System.out.println("Total tours with past departure dates: " + count);
    }

    //List total booking amount for tours with departure dates later than current date
    public void listTotalBookingAmount(BookingManager bm) {
        java.util.Date currentDate = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        System.out.println("======= TOURS WITH FUTURE DEPARTURE DATES =======");
        System.out.println("Current Date: " + sdf.format(currentDate));
        System.out.println("-------------------------------------------------");
        System.out.printf("%-10s | %-25s | %-10s | %-15s%n", "Tour ID", "Tour Name", "Bookings", "Total Amount");
        System.out.println("-------------------------------------------------");

        double grandTotal = 0;

        for (Tour tour : tourList) {
            if (tour.getDepartureDate().after(currentDate)) {
                // Count bookings and calculate total amount for this tour
                int bookingCount = 0;
                double totalAmount = 0;
                for (Booking booking : bm.getBookingList()) {
                    if (booking.getTourId().equalsIgnoreCase(tour.getTourId())) {
                        bookingCount++;
                        totalAmount += booking.getTotalAmount();
                    }
                }

                grandTotal += totalAmount;

                System.out.printf("%-10s | %-25s | %-10d | $%-15.2f%n",
                        tour.getTourId(),
                        tour.getTourName(),
                        bookingCount,
                        totalAmount);
            }
        }

        System.out.println("-------------------------------------------------");
        System.out.printf("Grand Total: $%.2f%n", grandTotal);
    }

    public final void readFromFile() {
        tourList.clear();
        List<String> lines = FileUtils.readLines(pathFile);
        System.out.println("Lines read: " + lines.size());

        for (String line : lines) {
            Tour t = textToObject(line);
            if (t != null) {
                tourList.add(t);
                System.out.println("Loaded tour: " + t.getTourId() + " - " + t.getTourName());
            } else {
                System.out.println("Failed to parse line: " + line);
            }
        }
        System.out.println("Total tours loaded: " + tourList.size());
    }

    public Tour textToObject(String temp) {
        try {
            String[] part = temp.split(",", 9);

            if (part.length != 9) {
                System.out.println("Error: Expected 9 parts, got " + part.length + " for line: " + temp);
                return null;
            }

            for (int i = 0; i < part.length; i++) {
                part[i] = part[i].trim();
            }

            Tour t = new Tour();

            t.setTourId(part[0]);
            t.setTourName(part[1]);
            t.setTime(part[2]);
            t.setPrice(Double.parseDouble(part[3]));
            t.setHomeId(part[4]);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);

            t.setDepartureDate(sdf.parse(part[5]));
            t.setEndDate(sdf.parse(part[6]));

            t.setTourist(Integer.parseInt(part[7]));
            t.setBooking(Boolean.parseBoolean(part[8]));

            return t;

        } catch (Exception e) {
            System.out.println("Error parsing tour line: " + e.getMessage());
            return null;
        }
    }

}
