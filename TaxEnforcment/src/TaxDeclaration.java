import java.time.LocalDate;

abstract class TaxDeclaration {
    private String declarationId;
    private String taxpayerName;
    private String taxpayerTIN;
    private LocalDate declarationDate;
    private double taxAmount;
    private boolean isPaid;

    public TaxDeclaration(String declarationId, String taxpayerName, String taxpayerTIN, LocalDate declarationDate, double taxAmount, boolean isPaid) {
        this.declarationId = declarationId;
        this.taxpayerName = taxpayerName;
        this.taxpayerTIN = taxpayerTIN;
        this.declarationDate = declarationDate;
        this.taxAmount = taxAmount;
        this.isPaid = isPaid;
    }

    public String getDeclarationId() {
        return declarationId;
    }

    public String getTaxpayerName() {
        return taxpayerName;
    }

    public String getTaxpayerTIN() {
        return taxpayerTIN;
    }

    public LocalDate getDeclarationDate() {
        return declarationDate;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public abstract void calculateTax();

    public abstract boolean validateDeclaration();

    public abstract void generateReceipt();

    public abstract void enforceCompliance();

}
