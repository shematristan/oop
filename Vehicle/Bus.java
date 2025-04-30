class Bus extends Vehicle {
    private int passengerCapacity;

    public Bus(String vehicleId, String ownerName, int yearOfFabrication, String registrationNumber, double baseTaxRate, int passengerCapacity) {
        super(vehicleId, ownerName, yearOfFabrication, registrationNumber, baseTaxRate, "Bus");
        if (passengerCapacity <= 0) {
            throw new IllegalArgumentException("Passenger capacity must be positive.");
        }
        this.passengerCapacity = passengerCapacity;
    }

    @Override
    public double calculateTax() {
        double tax = getBaseTaxRate();
        tax *= (1 + (passengerCapacity / 10) * 0.02); // 2% per 10 passengers
        if (java.time.Year.now().getValue() - getYearOfFabrication() > 20) {
            tax *= 1.1; // 10% increase
        }
        return tax;
    }

    @Override
    public void generateTaxReport() {
        System.out.println(toString() + ", Tax: " + calculateTax());
    }

}
