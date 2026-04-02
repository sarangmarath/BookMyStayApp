import java.util.*;

// Room class for UC6-style display
class Room {
    String type;
    int beds;
    int size;
    double price;
    int available;

    public Room(String type, int beds, int size, double price, int available) {
        this.type = type;
        this.beds = beds;
        this.size = size;
        this.price = price;
        this.available = available;
    }

    public void display() {
        System.out.println(type + " Room:");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
        System.out.println("Available Rooms: " + available);
        System.out.println();
    }

    public boolean bookRoom() {
        if (available > 0) {
            available--;
            return true;
        }
        return false;
    }
}

// Reservation class
class Reservation {
    private int reservationId;
    private String guestName;
    private String roomType;

    public Reservation(int reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public int getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
               ", Guest: " + guestName +
               ", Room Type: " + roomType;
    }
}

// Booking Service
class BookingService {
    private Queue<Reservation> requestQueue;
    private List<Room> rooms;
    private Map<String, Set<String>> roomAllocations;
    private int roomCounter = 1;

    public BookingService(Queue<Reservation> requestQueue, List<Room> rooms) {
        this.requestQueue = requestQueue;
        this.rooms = rooms;
        this.roomAllocations = new HashMap<>();
    }

    private String generateRoomId(String roomType) {
        return roomType.substring(0, 2).toUpperCase() + roomCounter++;
    }

    public void processNext() {
        if (requestQueue.isEmpty()) {
            System.out.println("No booking requests.");
            return;
        }

        Reservation r = requestQueue.poll();
        System.out.println("\nProcessing: " + r);

        for (Room room : rooms) {
            if (room.type.equalsIgnoreCase(r.getRoomType())) {
                if (room.bookRoom()) {
                    String roomId = generateRoomId(r.getRoomType());
                    roomAllocations.computeIfAbsent(r.getRoomType(), k -> new HashSet<>()).add(roomId);
                    System.out.println(" Booking Confirmed!");
                    System.out.println(" Guest: " + r.getGuestName());
                    System.out.println(" Room Type: " + r.getRoomType());
                    System.out.println(" Allocated Room ID: " + roomId);
                } else {
                    System.out.println(" No rooms available for type: " + r.getRoomType());
                }
                return;
            }
        }

        System.out.println(" Room type not found for: " + r.getRoomType());
    }

    public void showAllocations() {
        System.out.println("\nRoom Allocations:");
        for (String type : roomAllocations.keySet()) {
            System.out.println(type + " -> " + roomAllocations.get(type));
        }
    }
}

// Main App
public class HotelBookingApp {
    public static void main(String[] args) {

        // Step 1: UC6-style room inventory
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Single", 1, 250, 1500.0, 5));
        rooms.add(new Room("Double", 2, 400, 2500.0, 3));
        rooms.add(new Room("Suite", 3, 750, 5000.0, 2));

        System.out.println("Hotel Room Inventory Status\n");
        for (Room room : rooms) room.display();

        // Step 2: Booking Queue
        Queue<Reservation> requestQueue = new LinkedList<>();
        requestQueue.add(new Reservation(1, "Alice", "Single"));
        requestQueue.add(new Reservation(2, "Bob", "Double"));
        requestQueue.add(new Reservation(3, "Charlie", "Suite"));
        requestQueue.add(new Reservation(4, "David", "Suite")); // Should fail if not enough suites

        // Step 3: Booking Service
        BookingService bookingService = new BookingService(requestQueue, rooms);

        // Step 4: Process all requests
        while (!requestQueue.isEmpty()) {
            bookingService.processNext();
        }

        // Step 5: Show final allocations
        bookingService.showAllocations();

        // Step 6: Show updated inventory
        System.out.println("\nUpdated Hotel Room Inventory Status:\n");
        for (Room room : rooms) room.display();
    }
}