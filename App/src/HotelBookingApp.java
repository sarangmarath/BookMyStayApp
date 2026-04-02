import java.util.*;

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

    public boolean bookRoom() {
        if (available > 0) {
            available--;
            return true;
        }
        return false;
    }

    public void display() {
        System.out.println(type + " Room:");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
        System.out.println("Available Rooms: " + available + "\n");
    }
}

// --- Custom Exception ---
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) { super(message); }
}

// --- Reservation ---
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + ", Room Type: " + roomType;
    }
}

// --- Booking Validator ---
class ReservationValidator {
    public void validate(String guestName, String roomType, List<Room> rooms)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        boolean validType = false;
        for (Room r : rooms) {
            if (r.type.equalsIgnoreCase(roomType)) {
                validType = true;
                if (r.available == 0) {
                    throw new InvalidBookingException("Room not available");
                }
            }
        }

        if (!validType) throw new InvalidBookingException("Invalid room type");
    }
}

// --- Booking Queue (FIFO) ---
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
        System.out.println("Booking request added for " + r.guestName);
    }

    public Reservation nextRequest() { return queue.poll(); }
    public boolean isEmpty() { return queue.isEmpty(); }
}

// --- Booking History ---
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();
    public void addReservation(Reservation r) { history.add(r); }
    public List<Reservation> getHistory() { return history; }
}

// --- Booking Service ---
class BookingService {
    private BookingRequestQueue queue;
    private List<Room> rooms;
    private BookingHistory history;

    public BookingService(BookingRequestQueue queue, List<Room> rooms, BookingHistory history) {
        this.queue = queue;
        this.rooms = rooms;
        this.history = history;
    }

    public void processNext() {
        if (queue.isEmpty()) return;
        Reservation r = queue.nextRequest();
        for (Room room : rooms) {
            if (room.type.equalsIgnoreCase(r.roomType)) {
                if (room.bookRoom()) {
                    System.out.println("Booking Confirmed for " + r.guestName);
                    history.addReservation(r);
                } else {
                    System.out.println("No rooms available for " + r.roomType);
                }
                return;
            }
        }
    }
}

// --- Report Service ---
class BookingReportService {
    public void generateReport(List<Reservation> history) {
        System.out.println("\nBooking History Report:");
        for (Reservation r : history) System.out.println(r);
    }
}

// --- Main Class ---
public class HotelBookingApp {
    public static void main(String[] args) {

        // Rooms
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Single", 1, 250, 1500.0, 5));
        rooms.add(new Room("Double", 2, 400, 2500.0, 3));
        rooms.add(new Room("Suite", 3, 750, 5000.0, 2));

        System.out.println("Hotel Room Inventory Status:");
        for (Room r : rooms) r.display();

        // Queue, history, validator
        BookingRequestQueue queue = new BookingRequestQueue();
        BookingHistory history = new BookingHistory();
        ReservationValidator validator = new ReservationValidator();

        Scanner sc = new Scanner(System.in);

        // Sample bookings
        String[] names = {"Abhi", "Subha", "Vanmathi", "David"};
        String[] types = {"Single", "Double", "Suite", "Suite"};

        for (int i = 0; i < names.length; i++) {
            try {
                validator.validate(names[i], types[i], rooms);
                Reservation r = new Reservation(names[i], types[i]);
                queue.addRequest(r);
            } catch (InvalidBookingException e) {
                System.out.println("Booking failed for " + names[i] + ": " + e.getMessage());
            }
        }

        // Process queue
        BookingService bookingService = new BookingService(queue, rooms, history);
        while (!queue.isEmpty()) bookingService.processNext();

        // Show final report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history.getHistory());
    }
}