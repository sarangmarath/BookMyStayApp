import java.util.*;

// Room class (UC6)
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

    public int getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
               ", Guest: " + guestName +
               ", Room Type: " + roomType;
    }
}

// Queue Manager
class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Request added: " + reservation);
    }

    public void showQueue() {
        if (queue.isEmpty()) {
            System.out.println("No booking requests in queue.");
            return;
        }
        System.out.println("\nBooking Requests in Queue (FIFO Order):");
        for (Reservation r : queue) {
            System.out.println(r);
        }
    }

    public Reservation processNextRequest(List<Room> rooms) {
        if (queue.isEmpty()) {
            System.out.println("No requests to process.");
            return null;
        }

        Reservation r = queue.poll();
        // Try to book the room
        for (Room room : rooms) {
            if (room.type.equalsIgnoreCase(r.getRoomType())) {
                if (room.bookRoom()) {
                    System.out.println("Processing request: " + r + " ✅ Booked successfully");
                } else {
                    System.out.println("Processing request: " + r + " ❌ No rooms available");
                }
                return r;
            }
        }

        System.out.println("Processing request: " + r + " ❌ Room type not found");
        return r;
    }
}

// Main Application
public class HotelBookingApp {
    public static void main(String[] args) {

        // UC6 – Inventory
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Single",1,250,1500.0,5));
        rooms.add(new Room("Double",2,400,2500.0,3));
        rooms.add(new Room("Suite",3,750,5000.0,2));

        System.out.println("Hotel Room Inventory Status\n");
        for (Room room : rooms) {
            room.display();
        }

        // Booking Queue
        BookingRequestQueue requestQueue = new BookingRequestQueue();

        // Adding booking requests
        requestQueue.addRequest(new Reservation(1, "Alice", "Single"));
        requestQueue.addRequest(new Reservation(2, "Bob", "Double"));
        requestQueue.addRequest(new Reservation(3, "Charlie", "Suite"));

        // Show queue
        requestQueue.showQueue();

        // Process requests
        System.out.println("\n--- Processing Requests ---");
        requestQueue.processNextRequest(rooms);
        requestQueue.processNextRequest(rooms);

        // Remaining queue
        requestQueue.showQueue();

        // Updated inventory after booking
        System.out.println("\nUpdated Hotel Room Inventory Status:\n");
        for (Room room : rooms) {
            room.display();
        }
    }
}