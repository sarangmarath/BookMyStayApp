import java.util.*;

// Abstract Room class
abstract class Room {
    private String type;
    private int beds;
    private int size;
    private double price;

    public Room(String type, int beds, int size, double price) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public String getType() { return type; }
    public int getBeds() { return beds; }
    public int getSize() { return size; }
    public double getPrice() { return price; }

    public abstract void displayDetails(int availability);
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 250, 1500.0);
    }

    public void displayDetails(int availability) {
        System.out.println("Single Room:");
        System.out.println("Beds: " + getBeds());
        System.out.println("Size: " + getSize() + " sqft");
        System.out.println("Price per night: " + getPrice());
        System.out.println("Available Rooms: " + availability + "\n");
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 400, 2500.0);
    }

    public void displayDetails(int availability) {
        System.out.println("Double Room:");
        System.out.println("Beds: " + getBeds());
        System.out.println("Size: " + getSize() + " sqft");
        System.out.println("Price per night: " + getPrice());
        System.out.println("Available Rooms: " + availability + "\n");
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 750, 5000.0);
    }

    public void displayDetails(int availability) {
        System.out.println("Suite Room:");
        System.out.println("Beds: " + getBeds());
        System.out.println("Size: " + getSize() + " sqft");
        System.out.println("Price per night: " + getPrice());
        System.out.println("Available Rooms: " + availability + "\n");
    }
}

// Inventory class (read-only usage here)
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }
}

// Search Service (read-only)
class RoomSearchService {

    public void searchAvailableRooms(List<Room> rooms, RoomInventory inventory) {

        System.out.println("Available Rooms:\n");

        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getType());

            // Filter unavailable rooms
            if (available > 0) {
                room.displayDetails(available);
            }
        }
    }
}

// Main class
public class HotelBookingApp {

    public static void main(String[] args) {

        // Create room objects
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single Room", 5);
        inventory.addRoom("Double Room", 0); // unavailable
        inventory.addRoom("Suite Room", 2);

        // Perform search (read-only)
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(rooms, inventory);
    }
}
