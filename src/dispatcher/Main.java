package dispatcher;

import Manager.BookingManager;
import Manager.HomestayManager;
import Manager.TourManager;
import model.Booking;
import model.Tour;
import tools.Inputter;

public class Main {

    public static void main(String[] args) {
        Inputter input = new Inputter();
        HomestayManager hm = new HomestayManager();
        TourManager tm = new TourManager();
        BookingManager bm = new BookingManager();
        int choice;
        do {
            choice = input.getInt("=========== MENU ===========\n"
                    + "1. Add a new Tour\n"
                    + "2. Update a Tour by ID\n"
                    + "3. List the Tours with departure dates earlier than current date\n"
                    + "4. List total Booking amount for Tours with departure dates later than current date\n"
                    + "5. Add a new Booking\n"
                    + "6. Remove a Booking by booking ID\n"
                    + "7. Update a Booking by booking ID\n"
                    + "8. List all Booking by full or partial name\n"
                    + "9. Statistics total number of tourists\n"
                    + "10. Quit\n"
                    + "------------------------------------------\n"
                    + "Enter your choice: ");
            switch (choice) {
                case 1:
                    Tour tour = input.inputTour();
                    tm.addNew(hm, tour);
                    break;
                case 2:
                    String tourId = input.getString("Enter tour ID to update: ");
                    Tour updatedTour = input.updateTour();
                    tm.updateTour(tourId, hm, updatedTour);
                    break;
                case 3:
                    tm.listPastTours();
                    break;
                case 4:
                    tm.listTotalBookingAmount(bm);
                    break;
                case 5:
                    Booking newBooking = input.inputBooking();
                    bm.addNewBooking(hm, tm, newBooking);
                    break;
                case 6:
                    String removeBooking = input.getString("Enter booking ID to remove: ");
                    bm.removeBooking(removeBooking);
                    break;
                case 7:
                    String bookingId = input.getString("Enter Booking ID to update: ");
                    Booking updatedBooking = input.updateBooking();
                    bm.updateBooking(bookingId, hm, tm, updatedBooking);
                    break;
                case 8:
                    String fullName = input.getString("Enter full name to search: ");
                    bm.listByFullName(fullName, tm);
                    break;
                case 9:
                    bm.statisticsTotalTourists(tm);
                    break;
                case 10:
                    System.out.println("END...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 10);
    }

}
