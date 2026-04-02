import java.io.*;
import java.util.*;

// ---------------------- Serializable Inventory Class ----------------------
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        roomAvailability.put("Single Room", 5);
        roomAvailability.put("Double Room", 3);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }

    public void displayInventory() {
        System.out.println("\nInventory:");
        for (Map.Entry<String, Integer> entry : roomAvailability.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

// ---------------------- Serializable Booking Data ----------------------
class BookingData implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, String> reservations;

    public BookingData() {
        reservations = new HashMap<>();
    }
}

// ---------------------- Persistence Service ----------------------
class PersistenceService {

    private static final String FILE_NAME = "hotel_data.ser";

    // Save system state
    public static void save(RoomInventory inventory, BookingData data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(inventory);
            out.writeObject(data);
            System.out.println("\nSystem state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load system state
    public static Object[] load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            RoomInventory inventory = (RoomInventory) in.readObject();
            BookingData data = (BookingData) in.readObject();
            System.out.println("\nSystem state restored successfully.");
            return new Object[]{inventory, data};
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading data. Starting with safe state.");
        }
        return null;
    }
}

// ---------------------- Main Class ----------------------
public class HotelBookingApp {

    public static void main(String[] args) {

        RoomInventory inventory;
        BookingData bookingData;

        // Step 1: Load previous state
        Object[] loaded = PersistenceService.load();

        if (loaded != null) {
            inventory = (RoomInventory) loaded[0];
            bookingData = (BookingData) loaded[1];
        } else {
            inventory = new RoomInventory();
            bookingData = new BookingData();
        }

        // Step 2: Simulate booking
        System.out.println("\n--- Booking Simulation ---");
        String bookingId = "R101";
        String roomType = "Single Room";

        if (inventory.getRoomAvailability().get(roomType) > 0) {
            bookingData.reservations.put(bookingId, roomType);

            int current = inventory.getRoomAvailability().get(roomType);
            inventory.updateAvailability(roomType, current - 1);

            System.out.println("Booking successful: " + bookingId + " -> " + roomType);
        } else {
            System.out.println("Booking failed: No " + roomType + " available.");
        }

        // Step 3: Display current state
        inventory.displayInventory();
        System.out.println("Bookings: " + bookingData.reservations);

        // Step 4: Save state before shutdown
        PersistenceService.save(inventory, bookingData);
    }
}