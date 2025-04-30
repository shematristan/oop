class Motorcycle extends Vehicle {
    private int engineCapacity;

    public Motorcycle(String vehicleId, String ownerName, int yearOfFabrication, String registrationNumber, double baseTaxRate, int engineCapacity) {
        super(vehicleId, ownerName, yearOfFabrication, registrationNumber, baseTaxRate, "Motorcycle");
        if (engineCapacity <= 0) {
            throw new IllegalArgumentException("Engine capacity must be positive.");
        }
        this.engineCapacity = engineCapacity;
    }

    @Override
    public double calculateTax() {
        double tax = getBaseTaxRate();
        if (engineCapacity > 500) {
            tax *= 1.2; // 20% extra tax
        }
        int age = java.time.Year.now().getValue() - getYearOfFabrication();
        tax *= (1 - (age / 5) * 0.05); // 5% reduction every 5 years
        return tax;
    }

    @Override
    public void generateTaxReport() {
        System.out.println(toString() + ", Tax: " + calculateTax());
    }
    
}
