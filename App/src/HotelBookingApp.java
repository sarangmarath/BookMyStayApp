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
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Room Type: " + roomType;
    }
}

// Booking history
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void addReservation(Reservation r) { history.add(r); }
    public List<Reservation> getHistory() { return history; }
}

// Booking Service
class BookingService {
    private Queue<Reservation> queue;
    private List<Room> rooms;
    private BookingHistory history;

    public BookingService(Queue<Reservation> queue, List<Room> rooms, BookingHistory history) {
        this.queue = queue;
        this.rooms = rooms;
        this.history = history;
    }

    public void processNext() {
        if (queue.isEmpty()) {
            System.out.println("No booking requests.");
            return;
        }

        Reservation r = queue.poll();
        System.out.println("\nProcessing booking: " + r);

        for (Room room : rooms) {
            if (room.type.equalsIgnoreCase(r.getRoomType())) {
                if (room.bookRoom()) {
                    System.out.println("Booking Confirmed for " + r.getGuestName() +
                                       " in " + r.getRoomType() + " room.");
                    history.addReservation(r); // Add to history
                } else {
                    System.out.println("No rooms available for type: " + r.getRoomType());
                }
                return;
            }
        }
        System.out.println("Room type not found: " + r.getRoomType());
    }
}

// Report Service
class BookingReportService {
    public void generateReport(List<Reservation> history) {
        System.out.println("\nBooking History Report:");
        for (Reservation r : history) {
            System.out.println(r);
        }
    }
}

// Main class
public class HotelBookingApp {
    public static void main(String[] args) {

        System.out.println("Hotel Booking App - Full Flow");

        // Step 1: Rooms
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Single", 1, 250, 1500.0, 5));
        rooms.add(new Room("Double", 2, 400, 2500.0, 3));
        rooms.add(new Room("Suite", 3, 750, 5000.0, 2));

        System.out.println("\nHotel Room Inventory Status:");
        for (Room room : rooms) room.display();

        // Step 2: Booking queue
        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Abhi", "Single"));
        bookingQueue.add(new Reservation("Subha", "Double"));
        bookingQueue.add(new Reservation("Vanmathi", "Suite"));

        // Step 3: History
        BookingHistory history = new BookingHistory();

        // Step 4: Process bookings
        BookingService bookingService = new BookingService(bookingQueue, rooms, history);
        while (!bookingQueue.isEmpty()) {
            bookingService.processNext();
        }

        // Step 5: Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history.getHistory());
    }
}