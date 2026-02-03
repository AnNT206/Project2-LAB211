package model;

public class Homestay {

    private String homeID;
    private String homeName;
    private int roomNumber;
    private String address;
    private int maximumcapacity;

    //constructor
    public Homestay() {
    }

    public Homestay(String homeID, String homeName, int roomNumber, String address, int maximumcapacity) {
        this.homeID = homeID;
        this.homeName = homeName;
        this.roomNumber = roomNumber;
        this.address = address;
        this.maximumcapacity = maximumcapacity;
    }

    //getter and setter
    public String getHomeID() {
        return homeID;
    }

    public void setHomeID(String homeID) {
        this.homeID = homeID;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaximumcapacity() {
        return maximumcapacity;
    }

    public void setMaximumcapacity(int maximumcapacity) {
        this.maximumcapacity = maximumcapacity;
    }

    @Override
    public String toString(){
        return String.format("|%6s|%-25s|%-2s|%-3s||",this.homeID, this.homeName, this.roomNumber, this.address, this.maximumcapacity);
    }
}
