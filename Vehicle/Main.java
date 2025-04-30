import java.util.ArrayList;
import java.util.Scanner;

public class Main {
     private static ArrayList<Vehicle> vehicles = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Register a new vehicle");
            System.out.println("2. View registered vehicles");
            System.out.println("3. Calculate tax for all vehicles");
            System.out.println("4. Generate tax reports");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerVehicle(scanner);
                    break;
                case 2:
                    viewVehicles();
                    break;
                case 3:
                    calculateTaxForAll();
                    break;
                case 4:
                    generateTaxReports();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void registerVehicle(Scanner scanner) {
        System.out.print("Enter vehicle type (Car, Truck, Motorcycle, Bus, SUV): ");
        String type = scanner.nextLine();
        System.out.print("Enter vehicle ID: ");
        String vehicleId = scanner.nextLine();
        System.out.print("Enter owner name: ");
        String ownerName = scanner.nextLine();
        System.out.print("Enter year of fabrication: ");
        int yearOfFabrication = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter registration number: ");
        String registrationNumber = scanner.nextLine();
        System.out.print("Enter base tax rate: ");
        double baseTaxRate = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        for (Vehicle v : vehicles) {
            if (v.getRegistrationNumber().equals(registrationNumber)) {
                System.out.println("Error: Duplicate registration number.");
                return;
            }
        }

        switch (type.toLowerCase()) {
            case "car":
                System.out.print("Is the car electric (true/false): ");
                boolean isElectric = scanner.nextBoolean();
                vehicles.add(new Car(vehicleId, ownerName, yearOfFabrication, registrationNumber, baseTaxRate, isElectric));
                break;
            case "truck":
                System.out.print("Enter load capacity (in tons): ");
                double loadCapacity = scanner.nextDouble();
                vehicles.add(new Truck(vehicleId, ownerName, yearOfFabrication, registrationNumber, baseTaxRate, loadCapacity));
                break;
            case "motorcycle":
                System.out.print("Enter engine capacity (in cc): ");
                int engineCapacity = scanner.nextInt();
                vehicles.add(new Motorcycle(vehicleId, ownerName, yearOfFabrication, registrationNumber, baseTaxRate, engineCapacity));
                break;
            case "bus":
                System.out.print("Enter passenger capacity: ");
                int passengerCapacity = scanner.nextInt();
                vehicles.add(new Bus(vehicleId, ownerName, yearOfFabrication, registrationNumber, baseTaxRate, passengerCapacity));
                break;
            case "suv":
                System.out.print("Is the SUV four-wheel drive (true/false): ");
                boolean fourWheelDrive = scanner.nextBoolean();
                vehicles.add(new SUV(vehicleId, ownerName, yearOfFabrication, registrationNumber, baseTaxRate, fourWheelDrive));
                break;
            default:
                System.out.println("Invalid vehicle type.");
        }
    }

    private static void viewVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles registered.");
        } else {
            for (Vehicle v : vehicles) {
                System.out.println(v);
            }
        }
    }

    private static void calculateTaxForAll() {
        for (Vehicle v : vehicles) {
            System.out.println(v + ", Tax: " + v.calculateTax());
        }
    }

    private static void generateTaxReports() {
        for (Vehicle v : vehicles) {
            v.generateTaxReport();
        }
    } 

}
