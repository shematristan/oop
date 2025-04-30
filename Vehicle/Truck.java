public class Truck extends Vehicle{
    private double loadCapacity;

    public Truck(String vehicleId, String ownerName, int yearOfFabrication, String registrationNumber, double baseTaxRate, double loadCapacity) {
        super(vehicleId, ownerName, yearOfFabrication, registrationNumber, baseTaxRate, "Truck");
        if (loadCapacity <= 0) {
            throw new IllegalArgumentException("Load capacity must be positive.");
        }
        this.loadCapacity = loadCapacity;
    }

    @Override
    public double calculateTax() {
        double tax = getBaseTaxRate();
        if (java.time.Year.now().getValue() - getYearOfFabrication() > 15) {
            tax *= 1.15; // 15% extra tax
        }
        if (loadCapacity > 10) {
            tax *= 1.25; // 25% increase
        }
        return tax;
    }

    @Override
    public void generateTaxReport() {
        System.out.println(toString() + ", Tax: " + calculateTax());
    }

}
