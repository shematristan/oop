import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class PAYEDeclaration extends TaxDeclaration{
    private double grossSalary;

    public PAYEDeclaration(String declarationId, String taxpayerName, String taxpayerTIN, LocalDate declarationDate, double grossSalary, boolean isPaid) {
        super(declarationId, taxpayerName, taxpayerTIN, declarationDate, 0, isPaid);
        this.grossSalary = grossSalary;
    }

    @Override
    public void calculateTax() {
        if (grossSalary > 0) {
            double tax = 0;
            if (grossSalary <= 300000) {
                tax = grossSalary * 0.1; // 10% for lower bracket
            } else if (grossSalary <= 1000000) {
                tax = grossSalary * 0.2; // 20% for middle bracket
            } else {
                tax = grossSalary * 0.3; // 30% for higher bracket
            }
            super.setPaid(false);
            System.out.println("Tax calculated: " + tax);
        } else {
            System.out.println("Invalid gross salary!");
        }
    }

    @Override
    public boolean validateDeclaration() {
        if (grossSalary <= 0) {
            System.out.println("Gross salary must be greater than 0.");
            return false;
        }
        if (getDeclarationDate().isAfter(LocalDate.now())) {
            System.out.println("Declaration date cannot be in the future.");
            return false;
        }
        return true;
    }

    @Override
    public void generateReceipt() {
        System.out.println("Receipt for PAYE Declaration");
        System.out.println("Taxpayer Name: " + getTaxpayerName());
        System.out.println("TIN: " + getTaxpayerTIN());
        System.out.println("Declaration Date: " + getDeclarationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        System.out.println("Tax Amount: " + getTaxAmount());
        System.out.println("Payment Status: " + (isPaid() ? "Paid" : "Unpaid"));
    }

    @Override
    public void enforceCompliance() {
        if (!isPaid() && getDeclarationDate().plusMonths(1).withDayOfMonth(15).isBefore(LocalDate.now())) {
            System.out.println("Penalty applied for late payment.");
        }
    }

}
