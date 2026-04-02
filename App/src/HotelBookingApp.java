import java.util.*;

// --- Reservation ---
class Reservation {
    int reservationId;
    String guestName;
    String roomType;

    public Reservation(int reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + ", Guest: " + guestName + ", Room: " + roomType;
    }
}

// --- Add-On Service ---
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public double getCost() { return cost; }

    @Override
    public String toString() { return serviceName + " (₹" + cost + ")"; }
}

// --- Add-On Service Manager ---
class AddOnServiceManager {
    private Map<Integer, List<AddOnService>> serviceMap = new HashMap<>();

    public void addService(int reservationId, AddOnService service) {
        serviceMap.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
        System.out.println("Added " + service + " to Reservation " + reservationId);
    }

    public void showServices(int reservationId) {
        List<AddOnService> list = serviceMap.get(reservationId);
        if (list == null || list.isEmpty()) {
            System.out.println("No services for Reservation " + reservationId);
            return;
        }
        System.out.println("Services for Reservation " + reservationId + ": " + list);
    }

    public double calculateTotalCost(int reservationId) {
        List<AddOnService> list = serviceMap.get(reservationId);
        if (list == null) return 0;
        return list.stream().mapToDouble(AddOnService::getCost).sum();
    }
}

// --- Main ---
public class HotelBookingApp {
    public static void main(String[] args) {

        AddOnServiceManager manager = new AddOnServiceManager();

        // Simulated confirmed reservations
        Reservation r1 = new Reservation(1, "Alice", "Deluxe");
        Reservation r2 = new Reservation(2, "Bob", "Standard");

        System.out.println("Confirmed Reservations:");
        System.out.println(r1);
        System.out.println(r2);

        // Add-On Services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService spa = new AddOnService("Spa", 1500);

        // Assign services
        manager.addService(r1.reservationId, breakfast);
        manager.addService(r1.reservationId, wifi);
        manager.addService(r2.reservationId, spa);

        // Show services and total
        manager.showServices(r1.reservationId);
        System.out.println("Total Add-On Cost for Reservation " + r1.reservationId + ": ₹" +
                manager.calculateTotalCost(r1.reservationId));

        manager.showServices(r2.reservationId);
        System.out.println("Total Add-On Cost for Reservation " + r2.reservationId + ": ₹" +
                manager.calculateTotalCost(r2.reservationId));
    }
}