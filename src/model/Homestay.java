package model;

public class Homestay {

    private String homeID;
    private String homeName;
    private String roomNumber;
    private String address;
    private String maximumcapacity;

    //constructor
    public Homestay() {
    }

    public Homestay(String homeID, String homeName, String roomNumber, String address, String maximumcapacity) {
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

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMaximumcapacity() {
        return maximumcapacity;
    }

    public void setMaximumcapacity(String maximumcapacity) {
        this.maximumcapacity = maximumcapacity;
    }

    @Override
    public String toString(){
        return String.format("|%6s|%-25s|%-2s|%-3s||",this.homeID, this.homeName, this.roomNumber, this.address, this.maximumcapacity);
    }
}
