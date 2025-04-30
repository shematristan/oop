import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class TaxSystem {
    public static void main(String[] args) {
        List<TaxDeclaration> declarations = new ArrayList<>();

        PAYEDeclaration paye = new PAYEDeclaration("D001", "John Doe", "123456789", LocalDate.of(2023, 9, 10), 500000, false);
        if (paye.validateDeclaration()) {
            paye.calculateTax();
            paye.generateReceipt();
            paye.enforceCompliance();
            declarations.add(paye);
        }
    }

}
