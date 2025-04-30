abstract class Vehicle {
    private String vehicleId;
    private String ownerName;
    private int yearOfFabrication;
    private String registrationNumber;
    private double baseTaxRate;
    private String vehicleType;

    public Vehicle(String vehicleId, String ownerName, int yearOfFabrication, String registrationNumber, double baseTaxRate, String vehicleType) {
        if (yearOfFabrication > java.time.Year.now().getValue()) {
            throw new IllegalArgumentException("Year of fabrication cannot be in the future.");
        }
        this.vehicleId = vehicleId;
        this.ownerName = ownerName;
        this.yearOfFabrication = yearOfFabrication;
        this.registrationNumber = registrationNumber;
        this.baseTaxRate = baseTaxRate;
        this.vehicleType = vehicleType;
    }

    public abstract double calculateTax();

    public abstract void generateTaxReport();

    @Override
    public String toString() {
        return "Vehicle ID: " + vehicleId + ", Owner: " + ownerName + ", Year: " + yearOfFabrication +
               ", Registration: " + registrationNumber + ", Type: " + vehicleType;
    }

    // Getters and setters with validation
    public String getVehicleId() {
        return vehicleId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getYearOfFabrication() {
        return yearOfFabrication;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public double getBaseTaxRate() {
        return baseTaxRate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

}
