class SUV extends Vehicle{
    private boolean fourWheelDrive;

    public SUV(String vehicleId, String ownerName, int yearOfFabrication, String registrationNumber, double baseTaxRate, boolean fourWheelDrive) {
        super(vehicleId, ownerName, yearOfFabrication, registrationNumber, baseTaxRate, "SUV");
        this.fourWheelDrive = fourWheelDrive;
    }

    @Override
    public double calculateTax() {
        double tax = getBaseTaxRate();
        if (fourWheelDrive) {
            tax *= 1.1; // 10% increase
        }
        if (java.time.Year.now().getValue() - getYearOfFabrication() > 10) {
            tax *= 0.95; // 5% reduction
        }
        return tax;
    }

    @Override
    public void generateTaxReport() {
        System.out.println(toString() + ", Tax: " + calculateTax());
    }

}
