import java.util.*;

// ---------------------- Add-On Service Class ----------------------
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// ---------------------- Add-On Service Manager ----------------------
class AddOnServiceManager {

    // Map: reservationId -> list of services
    private Map<Integer, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add a service to a reservation
    public void addService(int reservationId, AddOnService service) {
        serviceMap.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(service);
        System.out.println("Added service to Reservation " + reservationId + ": " + service);
    }

    // Show all services for a reservation
    public void showServices(int reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services for Reservation " + reservationId);
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId + ":");
        for (AddOnService s : services) {
            System.out.println("- " + s);
        }
    }

    // Calculate total cost for a reservation
    public double calculateTotalCost(int reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);
        if (services == null) return 0;

        double total = 0;
        for (AddOnService s : services) {
            total += s.getCost();
        }
        return total;
    }
}

// ---------------------- Main Class ----------------------
public class HotelBookingApp {
    public static void main(String[] args) {

        System.out.println("=== Hotel Add-On Services Demo ===\n");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Sample reservations (IDs from previous booking system)
        int res1 = 1;
        int res2 = 2;

        // Create available services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService spa = new AddOnService("Spa", 1500);

        // Add services to reservations
        manager.addService(res1, breakfast);
        manager.addService(res1, wifi);
        manager.addService(res2, spa);

        // Display services for each reservation
        manager.showServices(res1);
        manager.showServices(res2);

        // Display total cost
        System.out.println("\nTotal Add-On Cost for Reservation " + res1 + ": ₹" +
                manager.calculateTotalCost(res1));
        System.out.println("Total Add-On Cost for Reservation " + res2 + ": ₹" +
                manager.calculateTotalCost(res2));
    }
}