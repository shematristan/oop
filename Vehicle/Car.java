class Car extends Vehicle {
    private boolean isElectric;

    public Car(String vehicleId, String ownerName, int yearOfFabrication, String registrationNumber, double baseTaxRate, boolean isElectric) {
        super(vehicleId, ownerName, yearOfFabrication, registrationNumber, baseTaxRate, "Car");
        this.isElectric = isElectric;
    }

    @Override
    public double calculateTax() {
        double tax = getBaseTaxRate();
        if (isElectric) {
            tax *= 0.8; // 20% discount
        }
        if (java.time.Year.now().getValue() - getYearOfFabrication() > 10) {
            tax *= 0.9; // 10% reduction
        }
        return tax;
    }

    @Override
    public void generateTaxReport() {
        System.out.println(toString() + ", Tax: " + calculateTax());
    }

}
