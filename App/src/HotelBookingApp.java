import java.util.*;

public class HotelBookingApp {
    public static void main(String[] args) {

        List<Room> rooms = new ArrayList<>();

        rooms.add(new Room("Single",1,250,1500,5));
        rooms.add(new Room("Double",2,400,2500,3));
        rooms.add(new Room("Suite",3,750,5000,2));

        System.out.println("Available Rooms:\n");

        for (Room r : rooms) {
            r.display();
        }
    }
}