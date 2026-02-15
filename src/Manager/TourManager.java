package Manager;

import java.util.ArrayList;
import java.util.List;
import model.Tour;
import tools.Inputter;
import tools.ValidationUtils;

public class TourManager {

    private List<Tour> tourList;
    private String pathFile;
    private boolean saved;
    private Inputter inputter;
    private ValidationUtils validator;

    //constructor
    public TourManager(Inputter inputter, ValidationUtils validator) {
        this.inputter = inputter;
        this.validator = validator;
        tourList = new ArrayList<>();
        this.saved = true;
        this.pathFile = "./Tours.txt";
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
        if ((findById(t.getTourId()) != null)) {
            return false;
        }

        //check homestay exist or not
        if (hm.findById(t.getHomeId()) == null) {
            System.out.println("HOme ID does not exist");
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
    
    //List tour
    public void listTour(){
        System.out.println("-------TOUR LIST-------");
        for (Tour t : tourList) {
            System.out.println(t);
        }
    }
}
