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

        // Validate only if homeId is being updated (not empty)
        if (updatedTour.getHomeId() != null && !updatedTour.getHomeId().trim().isEmpty()) {
            if (hm.findById(updatedTour.getHomeId()) == null) {
                System.out.println("Home ID does not exist");
                return false;
            }
        }

        // Validate dates only if both are provided
        if (updatedTour.getDepartureDate() != null && updatedTour.getEndDate() != null) {
            if (!updatedTour.getEndDate().after(updatedTour.getDepartureDate())) {
                System.out.println("End date must be after departure date");
                return false;
            }
        }

        // Validate tourist only if being updated (greater than 0)
        if (updatedTour.getTourist() > 0) {
            Homestay homestay = hm.findById(existing.getHomeId());
            if (updatedTour.getTourist() > homestay.getMaximumcapacity()) {
                System.out.println("Number of tourists exceeds maximum capacity of homestay");
                return false;
            }
        }

        // Update fields only if they are not empty/null/zero
        if (updatedTour.getTourName() != null && !updatedTour.getTourName().trim().isEmpty()) {
            existing.setTourName(updatedTour.getTourName());
        }

        if (updatedTour.getPrice() != 0) {
            existing.setPrice(updatedTour.getPrice());
        }

        if (updatedTour.getHomeId() != null && !updatedTour.getHomeId().trim().isEmpty()) {
            existing.setHomeId(updatedTour.getHomeId());
        }

        if (updatedTour.getDepartureDate() != null) {
            existing.setDepartureDate(updatedTour.getDepartureDate());
        }

        if (updatedTour.getEndDate() != null) {
            existing.setEndDate(updatedTour.getEndDate());
        }

        if (updatedTour.getTourist() > 0) {
            existing.setTourist(updatedTour.getTourist());
        }

        if (updatedTour.getTime() != null && !updatedTour.getTime().trim().isEmpty()) {
            existing.setTime(updatedTour.getTime());
        }

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

        // Collect tour data with booking amounts
        java.util.List<Object[]> tourData = new java.util.ArrayList<>();
        double grandTotal = 0;

        for (Tour tour : tourList) {
            if (tour.getDepartureDate().after(currentDate)) {
                // Calculate total booking amount for this tour
                double totalAmount = 0;
                int bookingCount = 0;

                for (model.Booking booking : bm.getBookingList()) {
                    if (booking.getTourId().equalsIgnoreCase(tour.getTourId())) {
                        bookingCount++;
                        totalAmount += booking.getTotalAmount();
                    }
                }

                grandTotal += totalAmount;

                // Store tour data: [tourId, tourName, bookingCount, totalAmount]
                tourData.add(new Object[]{tour.getTourId(), tour.getTourName(), bookingCount, totalAmount});
            }
        }

        // Sort by total amount in descending order
        tourData.sort((a, b) -> Double.compare((Double) b[3], (Double) a[3]));

        // Display results
        System.out.println("======= TOURS WITH FUTURE DEPARTURE DATES =======");
        System.out.println("Current Date: " + sdf.format(currentDate));
        System.out.println("-------------------------------------------------");
        System.out.printf("%-10s | %-25s | %-10s | %-15s%n", "Tour ID", "Tour Name", "Bookings", "Total Amount");
        System.out.println("-------------------------------------------------");

        for (Object[] data : tourData) {
            System.out.printf("%-10s | %-25s | %-10d | $%-15.2f%n",
                    data[0],
                    data[1],
                    data[2],
                    data[3]);
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
