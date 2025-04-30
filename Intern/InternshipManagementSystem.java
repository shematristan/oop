package Advanced_Internship_Management_Systems;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InternshipManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Student> students = new ArrayList<>();
    private static List<Supervisor> supervisors = new ArrayList<>();
    private static List<Company> companies = new ArrayList<>();
    private static List<Internship> internships = new ArrayList<>();
    
    public static void main(String[] args) {
        addDemoData();
        boolean running = true;
        System.out.println("=== Internship Management System ===");
        
        while (running) {
            displayMainMenu();
            int choice = ValidationUtil.getValidInt("Enter your choice: ", 1, 7);
            
            switch (choice) {
                case 1:
                    registerStudent();
                    break;
                case 2:
                    registerSupervisor();
                    break;
                case 3:
                    registerCompany();
                    break;
                case 4:
                    createInternship();
                    break;
                case 5:
                    viewAllInternships();
                    break;
                case 6:
                    searchInternship();
                    break;
                case 7:
                    running = false;
                    System.out.println("Thank you for using the Internship Management System!");
                    break;
            }
        }
    }
    
    private static void displayMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Register Student");
        System.out.println("2. Register Supervisor");
        System.out.println("3. Register Company");
        System.out.println("4. Create Internship");
        System.out.println("5. View All Internships");
        System.out.println("6. Search Internship");
        System.out.println("7. Exit");
    }
    
    private static void registerStudent() {
        System.out.println("\n=== Student Registration ===");
        
        try {
            Student student = new Student();
            students.add(student);
            System.out.println("Student registered successfully: " + student);
        } catch (IllegalArgumentException e) {
            System.out.println("Error registering student: " + e.getMessage());
        }
    }
    
    private static void registerSupervisor() {
        System.out.println("\n=== Supervisor Registration ===");
        
        try {
            Supervisor supervisor = new Supervisor();
            supervisors.add(supervisor);
            System.out.println("Supervisor registered successfully: " + supervisor);
        } catch (IllegalArgumentException e) {
            System.out.println("Error registering supervisor: " + e.getMessage());
        }
    }
    
    private static void registerCompany() {
        System.out.println("\n=== Company Registration ===");
        
        try {
            Company company = new Company();
            companies.add(company);
            System.out.println("Company registered successfully: " + company);
        } catch (IllegalArgumentException e) {
            System.out.println("Error registering company: " + e.getMessage());
        }
    }
    
    private static void createInternship() {
        System.out.println("\n=== Internship Creation ===");
        
        if (students.isEmpty()) {
            System.out.println("No students registered. Please register a student first.");
            return;
        }
        
        if (supervisors.isEmpty()) {
            System.out.println("No supervisors registered. Please register a supervisor first.");
            return;
        }
        
        if (companies.isEmpty()) {
            System.out.println("No companies registered. Please register a company first.");
            return;
        }
        
        
        System.out.println("\nAvailable Students:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i+1) + ". " + students.get(i).getFullName() + " (" + students.get(i).getUniversity() + ")");
        }
        
        int studentIndex = ValidationUtil.getValidInt("Select student (number): ", 1, students.size()) - 1;
        Student selectedStudent = students.get(studentIndex);
        

        boolean hasActiveInternship = false;
        for (Internship internship : internships) {
            if (internship.getStudent().getStudentId().equals(selectedStudent.getStudentId()) 
                && (internship.getStatus().equals("PENDING") || internship.getStatus().equals("ONGOING"))) {
                hasActiveInternship = true;
                break;
            }
        }
        
        if (hasActiveInternship) {
            System.out.println("Error: This student already has an active internship.");
            return;
        }
        System.out.println("\nAvailable Companies:");
        for (int i = 0; i < companies.size(); i++) {
            System.out.println((i+1) + ". " + companies.get(i).getName() + " (" + companies.get(i).getIndustryType() + ")");
        }
        
        int companyIndex = ValidationUtil.getValidInt("Select company (number): ", 1, companies.size()) - 1;
        Company selectedCompany = companies.get(companyIndex);
        

        System.out.println("\nAvailable Supervisors:");
        for (int i = 0; i < supervisors.size(); i++) {
            System.out.println((i+1) + ". " + supervisors.get(i).getFullName() + " (" + supervisors.get(i).getQualification() + ")");
        }
        
        int supervisorIndex = ValidationUtil.getValidInt("Select supervisor (number): ", 1, supervisors.size()) - 1;
        Supervisor selectedSupervisor = supervisors.get(supervisorIndex);
        
        
        LocalDate startDate = ValidationUtil.getValidDate("Enter start date");
        LocalDate endDate = ValidationUtil.getValidDate("Enter end date");
    
        if (endDate.isBefore(startDate)) {
            System.out.println("Error: End date cannot be before start date.");
            return;
        }
        
        long weeks = ChronoUnit.WEEKS.between(startDate, endDate);
        if (weeks < 6) {
            System.out.println("Error: Internship must be at least 6 weeks long.");
            return;
        }
        
        // Generate internship ID
        String internshipId = "INT" + (internships.size() + 1) + "-" + selectedStudent.getUniversity();
        
        // Select internship type
        String internshipType = ValidationUtil.getValidChoice("Select internship type:", "ULK", "UR", "AUCA", "UK", "Remote");
        
        // Create the specific internship type
        Internship internship = null;
        
        try {
            switch (internshipType) {
                case "ULK":
                    if (!selectedStudent.getUniversity().equals("ULK")) {
                        System.out.println("Error: Only ULK students can have ULK internships.");
                        return;
                    }
                    
                    if (!selectedSupervisor.getQualification().equals("Masters") && !selectedSupervisor.getQualification().equals("PhD")) {
                        System.out.println("Error: ULK internships require supervisors with Masters or PhD qualification.");
                        return;
                    }
                    
                    internship = new ULKInternship(internshipId, selectedStudent, selectedCompany.getName(), selectedSupervisor, startDate, endDate);
                    break;
                    
                case "UR":
                    if (!selectedStudent.getUniversity().equals("UR")) {
                        System.out.println("Error: Only UR students can have UR internships.");
                        return;
                    }
                    
                    // Check if internship duration is between 2 and 6 months
                    long months = ChronoUnit.MONTHS.between(startDate, endDate);
                    if (months < 2 || months > 6) {
                        System.out.println("Error: UR internships must be between 2 and 6 months.");
                        return;
                    }
                    
                    // Ask if they want an optional second supervisor
                    boolean needsSecondSupervisor = ValidationUtil.getYesNoInput("Do you want to assign a second supervisor?");
                    Supervisor secondSupervisor = null;
                    
                    if (needsSecondSupervisor) {
                        System.out.println("\nAvailable Supervisors for second supervisor:");
                        List<Supervisor> availableSupervisors = new ArrayList<>();
                        for (int i = 0; i < supervisors.size(); i++) {
                            if (!supervisors.get(i).getSupervisorId().equals(selectedSupervisor.getSupervisorId())) {
                                availableSupervisors.add(supervisors.get(i));
                                System.out.println((i+1) + ". " + supervisors.get(i).getFullName() + " (" + supervisors.get(i).getQualification() + ")");
                            }
                        }
                        
                        if (availableSupervisors.isEmpty()) {
                            System.out.println("No other supervisors available. Proceeding with one supervisor.");
                        } else {
                            int secondSupervisorIndex = ValidationUtil.getValidInt("Select second supervisor (number): ", 1, availableSupervisors.size()) - 1;
                            secondSupervisor = availableSupervisors.get(secondSupervisorIndex);
                        }
                    }
                    
                    internship = new URInternship(internshipId, selectedStudent, selectedCompany.getName(), selectedSupervisor, startDate, endDate, secondSupervisor);
                    break;
                    
                case "AUCA":
                    if (!selectedStudent.getUniversity().equals("AUCA")) {
                        System.out.println("Error: Only AUCA students can have AUCA internships.");
                        return;
                    }
                    
                    int communityHours = ValidationUtil.getValidInt("Enter required community service hours: ", 1, 100);
                    internship = new AUCAInternship(internshipId, selectedStudent, selectedCompany.getName(), selectedSupervisor, startDate, endDate, communityHours);
                    break;
                    
                case "UK":
                    if (!selectedStudent.getUniversity().equals("UK")) {
                        System.out.println("Error: Only UK students can have UK internships.");
                        return;
                    }
                    
                    // UK internship requires two supervisors
                    System.out.println("\nUK internships require a second supervisor (university supervisor).");
                    System.out.println("Available Supervisors for university supervisor:");
                    List<Supervisor> availableSupervisors = new ArrayList<>();
                    for (int i = 0; i < supervisors.size(); i++) {
                        if (!supervisors.get(i).getSupervisorId().equals(selectedSupervisor.getSupervisorId())) {
                            availableSupervisors.add(supervisors.get(i));
                            System.out.println((i+1) + ". " + supervisors.get(i).getFullName() + " (" + supervisors.get(i).getQualification() + ")");
                        }
                    }
                    
                    if (availableSupervisors.isEmpty()) {
                        System.out.println("Error: UK internships require two supervisors. No other supervisors available.");
                        return;
                    }
                    
                    int secondSupervisorIndex = ValidationUtil.getValidInt("Select university supervisor (number): ", 1, availableSupervisors.size()) - 1;
                    Supervisor universitySupervisor = availableSupervisors.get(secondSupervisorIndex);
                    
                    String certificationLevel = ValidationUtil.getValidChoice("Enter English proficiency level:", "A1", "A2", "B1", "B2", "C1", "C2");
                    internship = new UKInternship(internshipId, selectedStudent, selectedCompany.getName(), selectedSupervisor, startDate, endDate, universitySupervisor, certificationLevel);
                    break;
                    
                case "Remote":
                    // Remote internships can be for any university
                    String communicationPlatform = ValidationUtil.getValidChoice("Select primary communication platform:", "Email", "Zoom", "Teams", "Slack", "Other");
                    internship = new RemoteInternship(internshipId, selectedStudent, selectedCompany.getName(), selectedSupervisor, startDate, endDate, communicationPlatform);
                    break;
            }
            
            if (internship != null) {
                internships.add(internship);
                System.out.println("Internship created successfully:");
                System.out.println(internship);
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating internship: " + e.getMessage());
        }
    }
    
    private static void viewAllInternships() {
        System.out.println("\n=== All Internships ===");
        
        if (internships.isEmpty()) {
            System.out.println("No internships created yet.");
            return;
        }
        
        for (Internship internship : internships) {
            System.out.println(internship);
            System.out.println("-------------------");
        }
    }
    
    private static void searchInternship() {
        System.out.println("\n=== Search Internships ===");
        
        if (internships.isEmpty()) {
            System.out.println("No internships created yet.");
            return;
        }
        
        System.out.println("1. Search by Student Name");
        System.out.println("2. Search by University");
        System.out.println("3. Search by Company");
        System.out.println("4. Search by Status");
        
        int searchOption = ValidationUtil.getValidInt("Select search option: ", 1, 4);
        
        switch (searchOption) {
            case 1:
                String nameQuery = ValidationUtil.getValidString("Enter student name (or part of name): ");
                searchByStudentName(nameQuery);
                break;
            case 2:
                String university = ValidationUtil.getValidChoice("Select university:", "ULK", "UR", "AUCA", "UK");
                searchByUniversity(university);
                break;
            case 3:
                String companyName = ValidationUtil.getValidString("Enter company name (or part of name): ");
                searchByCompany(companyName);
                break;
            case 4:
                String status = ValidationUtil.getValidChoice("Select status:", "PENDING", "ONGOING", "COMPLETED");
                searchByStatus(status);
                break;
        }
    }
    
    private static void searchByStudentName(String nameQuery) {
        boolean found = false;
        System.out.println("\n=== Internships for Student: " + nameQuery + " ===");
        
        for (Internship internship : internships) {
            if (internship.getStudent().getFullName().toLowerCase().contains(nameQuery.toLowerCase())) {
                System.out.println(internship);
                System.out.println("-------------------");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No internships found for student name containing '" + nameQuery + "'");
        }
    }
    
    private static void searchByUniversity(String university) {
        boolean found = false;
        System.out.println("\n=== Internships for University: " + university + " ===");
        
        for (Internship internship : internships) {
            if (internship.getStudent().getUniversity().equals(university)) {
                System.out.println(internship);
                System.out.println("-------------------");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No internships found for university '" + university + "'");
        }
    }
    
    private static void searchByCompany(String companyName) {
        boolean found = false;
        System.out.println("\n=== Internships at Company: " + companyName + " ===");
        
        for (Internship internship : internships) {
            if (internship.getCompanyName().toLowerCase().contains(companyName.toLowerCase())) {
                System.out.println(internship);
                System.out.println("-------------------");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No internships found for company containing '" + companyName + "'");
        }
    }
    
    private static void searchByStatus(String status) {
        boolean found = false;
        System.out.println("\n=== Internships with Status: " + status + " ===");
        
        for (Internship internship : internships) {
            if (internship.getStatus().equals(status)) {
                System.out.println(internship);
                System.out.println("-------------------");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No internships found with status '" + status + "'");
        }
    }
    
    private static void addDemoData() {
        // Add demo students
        students.add(new Student("S001", "John Doe", "ULK", "john.doe@example.com"));
        students.add(new Student("S002", "Jane Smith", "UR", "jane.smith@example.com"));
        students.add(new Student("S003", "Alex Johnson", "AUCA", "alex.johnson@example.com"));
        students.add(new Student("S004", "Emily Brown", "UK", "emily.brown@example.com"));
        
        // Add demo supervisors
        supervisors.add(new Supervisor("SUP001", "Dr. Robert Wilson", "PhD", "robert.wilson@example.com"));
        supervisors.add(new Supervisor("SUP002", "Prof. Lisa Taylor", "Masters", "lisa.taylor@example.com"));
        supervisors.add(new Supervisor("SUP003", "Mike Anderson", "Bachelors", "mike.anderson@example.com"));
        supervisors.add(new Supervisor("SUP004", "Dr. Sarah Mitchell", "PhD", "sarah.mitchell@example.com"));
        
        // Add demo companies
        companies.add(new Company("C001", "Tech Solutions", "IT", "Kigali"));
        companies.add(new Company("C002", "Global Finance", "Finance", "Butare"));
        companies.add(new Company("C003", "Health Partners", "Health", "Gisenyi"));
        companies.add(new Company("C004", "Education First", "Education", "Kigali"));
    }
}

class ValidationUtil {
    private static final Scanner scanner = new Scanner(System.in);
    
    /**
     * Validates and retrieves a non-empty string input
     */
    public static String getValidString(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt + " ");
            input = scanner.nextLine().trim();
            
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Error: Input cannot be empty. Please try again.");
            }
        }
    }
    
    /**
     * Validates and retrieves an integer within specified range
     */
    public static int getValidInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + " ");
            try {
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Error: Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer.");
            }
        }
    }
    
    /**
     * Validates and retrieves a double within specified range
     */
    public static double getValidDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt + " ");
            try {
                String input = scanner.nextLine().trim();
                double value = Double.parseDouble(input);
                
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Error: Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }
    
    /**
     * Validates and retrieves an email address
     */
    public static String getValidEmail(String prompt) {
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        
        while (true) {
            System.out.print(prompt + " ");
            String email = scanner.nextLine().trim();
            
            if (emailPattern.matcher(email).matches()) {
                return email;
            } else {
                System.out.println("Error: Invalid email format. Please include an '@' symbol.");
            }
        }
    }
    
    /**
     * Validates and retrieves a choice from a set of options
     */
    public static String getValidChoice(String prompt, String... options) {
        while (true) {
            System.out.print(prompt + " [" + String.join(", ", options) + "]: ");
            String input = scanner.nextLine().trim();
            
            for (String option : options) {
                if (option.equalsIgnoreCase(input)) {
                    return option;
                }
            }
            
            System.out.println("Error: Invalid choice. Please select from the available options.");
        }
    }
    
    /**
     * Validates and retrieves a date in the format YYYY-MM-DD
     */
    public static LocalDate getValidDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        while (true) {
            System.out.print(prompt + " (YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();
            
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format. Please use YYYY-MM-DD format.");
            }
        }
    }
    
    /**
     * Validates and retrieves a yes/no response
     */
    public static boolean getYesNoInput(String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                System.out.println("Error: Please enter 'y' or 'n'.");
            }
        }
    }
}

class Student {
    private String studentId;
    private String fullName;
    private String university;
    private String email;
    
    // Constructor
    public Student(String studentId, String fullName, String university, String email) {
        setStudentId(studentId);
        setFullName(fullName);
        setUniversity(university);
        setEmail(email);
    }
    
    // Empty constructor for user input
    public Student() {
        this.studentId = ValidationUtil.getValidString("Enter student ID:");
        this.fullName = ValidationUtil.getValidString("Enter full name:");
        this.university = ValidationUtil.getValidChoice("Enter university:", "ULK", "UR", "AUCA", "UK");
        this.email = ValidationUtil.getValidEmail("Enter email:");
    }
    
    // Getters and setters with validation
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Student ID cannot be empty");
        }
        this.studentId = studentId;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        this.fullName = fullName;
    }
    
    public String getUniversity() {
        return university;
    }
    
    public void setUniversity(String university) {
        if (university == null) {
            throw new IllegalArgumentException("University cannot be null");
        }
        
        String uni = university.trim().toUpperCase();
        if (!uni.equals("ULK") && !uni.equals("UR") && !uni.equals("AUCA") && !uni.equals("UK")) {
            throw new IllegalArgumentException("University must be ULK, UR, AUCA, or UK");
        }
        this.university = uni;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email must contain @ symbol");
        }
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", university='" + university + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

class Supervisor {
    private String supervisorId;
    private String fullName;
    private String qualification;
    private String email;
    
    // Constructor
    public Supervisor(String supervisorId, String fullName, String qualification, String email) {
        setSupervisorId(supervisorId);
        setFullName(fullName);
        setQualification(qualification);
        setEmail(email);
    }
    
    // Empty constructor for user input
    public Supervisor() {
        this.supervisorId = ValidationUtil.getValidString("Enter supervisor ID:");
        this.fullName = ValidationUtil.getValidString("Enter full name:");
        this.qualification = ValidationUtil.getValidChoice("Enter qualification:", "Bachelors", "Masters", "PhD");
        this.email = ValidationUtil.getValidEmail("Enter email:");
    }
    
    // Getters and setters with validation
    public String getSupervisorId() {
        return supervisorId;
    }
    
    public void setSupervisorId(String supervisorId) {
        if (supervisorId == null || supervisorId.trim().isEmpty()) {
            throw new IllegalArgumentException("Supervisor ID cannot be empty");
        }
        this.supervisorId = supervisorId;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }
        this.fullName = fullName;
    }
    
    public String getQualification() {
        return qualification;
    }
    
    public void setQualification(String qualification) {
        if (qualification == null) {
            throw new IllegalArgumentException("Qualification cannot be null");
        }
        
        String qual = qualification.trim();
        if (!qual.equals("Bachelors") && !qual.equals("Masters") && !qual.equals("PhD")) {
            throw new IllegalArgumentException("Qualification must be Bachelors, Masters, or PhD");
        }
        this.qualification = qual;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email must contain @ symbol");
        }
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "Supervisor{" +
                "supervisorId='" + supervisorId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", qualification='" + qualification + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

class Company {
    private String companyId;
    private String name;
    private String industryType;
    private String location;
    
    // Constructor
    public Company(String companyId, String name, String industryType, String location) {
        setCompanyId(companyId);
        setName(name);
        setIndustryType(industryType);
        setLocation(location);
    }
    
    // Empty constructor for user input
    public Company() {
        this.companyId = ValidationUtil.getValidString("Enter company ID:");
        this.name = ValidationUtil.getValidString("Enter company name:");
        this.industryType = ValidationUtil.getValidChoice("Enter industry type:", "IT", "Finance", "Health", "Education", "Manufacturing", "Other");
        this.location = ValidationUtil.getValidString("Enter location:");
    }
    
    // Getters and setters with validation
    public String getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(String companyId) {
        if (companyId == null || companyId.trim().isEmpty()) {
            throw new IllegalArgumentException("Company ID cannot be empty");
        }
        this.companyId = companyId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be empty");
        }
        this.name = name;
    }
    
    public String getIndustryType() {
        return industryType;
    }
    
    public void setIndustryType(String industryType) {
        if (industryType == null) {
            throw new IllegalArgumentException("Industry type cannot be null");
        }
        
        String industry = industryType.trim();
        if (!industry.equals("IT") && !industry.equals("Finance") && !industry.equals("Health") && 
            !industry.equals("Education") && !industry.equals("Manufacturing") && !industry.equals("Other")) {
            throw new IllegalArgumentException("Industry type must be IT, Finance, Health, Education, Manufacturing, or Other");
        }
        this.industryType = industry;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty");
        }
        this.location = location;
    }
    
    @Override
    public String toString() {
        return "Company{" +
                "companyId='" + companyId + '\'' +
                ", name='" + name + '\'' +
                ", industryType='" + industryType + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

abstract class Internship {
    private String internshipId;
    private Student student;
    private String companyName;
    private Supervisor supervisor;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status; // "PENDING", "ONGOING", "COMPLETED"
    private List<String> progressNotes;
    
    public Internship(String internshipId, Student student, String companyName, Supervisor supervisor, LocalDate startDate, LocalDate endDate) {
        setInternshipId(internshipId);
        setStudent(student);
        setCompanyName(companyName);
        setSupervisor(supervisor);
        setStartDate(startDate);
        setEndDate(endDate);
        
        // Default status is PENDING
        this.status = "PENDING";
        this.progressNotes = new ArrayList<>();
        
        // Validate the internship
        validateInternship();
    }
    
    // Abstract methods
    public abstract void assignSupervisor();
    public abstract void trackProgress();
    public abstract String generateReport();
    
    public void validateInternship() {
        // Check that start date is before end date
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        
        // Check minimum duration (6 weeks)
        long weeks = ChronoUnit.WEEKS.between(startDate, endDate);
        if (weeks < 6) {
            throw new IllegalArgumentException("Internship must be at least 6 weeks long");
        }
    }
    
    // Add a progress note
    public void addProgressNote(String note) {
        if (note != null && !note.trim().isEmpty()) {
            progressNotes.add(note);
        }
    }
    
    // Update status
    public void updateStatus(String status) {
        if (status != null && (status.equals("PENDING") || status.equals("ONGOING") || status.equals("COMPLETED"))) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Status must be PENDING, ONGOING, or COMPLETED");
        }
    }
    
    // Common report format
    protected String getBaseReport() {
        StringBuilder report = new StringBuilder();
        report.append("INTERNSHIP REPORT\n");
        report.append("----------------\n");
        report.append("ID: ").append(internshipId).append("\n");
        report.append("Student: ").append(student.getFullName()).append(" (").append(student.getUniversity()).append(")\n");
        report.append("Company: ").append(companyName).append("\n");
        report.append("Supervisor: ").append(supervisor.getFullName()).append(" (").append(supervisor.getQualification()).append(")\n");
        report.append("Duration: ").append(startDate).append(" to ").append(endDate).append("\n");
        report.append("Status: ").append(status).append("\n");
        report.append("Progress Notes:\n");
        
        if (progressNotes.isEmpty()) {
            report.append("  - No progress notes recorded\n");
        } else {
            for (String note : progressNotes) {
                report.append("  - ").append(note).append("\n");
            }
        }
        
        return report.toString();
    }
    
    // Getters and setters
    public String getInternshipId() {
        return internshipId;
    }
    
    public void setInternshipId(String internshipId) {
        if (internshipId == null || internshipId.trim().isEmpty()) {
            throw new IllegalArgumentException("Internship ID cannot be empty");
        }
        this.internshipId = internshipId;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        this.student = student;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        if (companyName == null || companyName.trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be empty");
        }
        this.companyName = companyName;
    }
    
    public Supervisor getSupervisor() {
        return supervisor;
    }
    
    public void setSupervisor(Supervisor supervisor) {
        if (supervisor == null) {
            throw new IllegalArgumentException("Supervisor cannot be null");
        }
        this.supervisor = supervisor;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null");
        }
        this.endDate = endDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public List<String> getProgressNotes() {
        return progressNotes;
    }
    
    @Override
    public String toString() {
        return "Internship ID: " + internshipId + "\n" +
               "Student: " + student.getFullName() + " (" + student.getUniversity() + ")\n" +
               "Company: " + companyName + "\n" +
               "Supervisor: " + supervisor.getFullName() + "\n" +
               "Duration: " + startDate + " to " + endDate + "\n" +
               "Status: " + status;
    }
}

class ULKInternship extends Internship {
    
    public ULKInternship(String internshipId, Student student, String companyName, Supervisor supervisor, 
                         LocalDate startDate, LocalDate endDate) {
        super(internshipId, student, companyName, supervisor, startDate, endDate);
        
        // Additional validation for ULK internship
        validateULKInternship();
    }
    
    private void validateULKInternship() {
        // Check that student is from ULK
        if (!getStudent().getUniversity().equals("ULK")) {
            throw new IllegalArgumentException("Student must be from ULK for ULK internship");
        }
        
        // Check that supervisor has Masters or PhD
        String qualification = getSupervisor().getQualification();
        if (!qualification.equals("Masters") && !qualification.equals("PhD")) {
            throw new IllegalArgumentException("Supervisor must have Masters or PhD qualification for ULK internship");
        }
    }
    
    @Override
    public void assignSupervisor() {
        System.out.println("ULK Supervisor assigned: " + getSupervisor().getFullName());
        addProgressNote("Supervisor assigned: " + getSupervisor().getFullName() + " (" + getSupervisor().getQualification() + ")");
    }
    
    @Override
    public void trackProgress() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter progress note for ULK internship " + getInternshipId() + ":");
        String note = scanner.nextLine();
        addProgressNote(note);
        System.out.println("Progress note added.");
    }
    
    @Override
    public String generateReport() {
        StringBuilder report = new StringBuilder(getBaseReport());
        report.append("\nULK SPECIFIC INFORMATION:\n");
        report.append("- Supervisor Qualification: ").append(getSupervisor().getQualification()).append("\n");
        report.append("- ULK internship requires minimum 6 weeks duration\n");
        
        return report.toString();
    }
    
    @Override
    public String toString() {
        return super.toString() + "\nType: ULK Internship";
    }
}

class URInternship extends Internship {
    private Supervisor secondSupervisor;
    
    public URInternship(String internshipId, Student student, String companyName, Supervisor supervisor,
                        LocalDate startDate, LocalDate endDate, Supervisor secondSupervisor) {
        super(internshipId, student, companyName, supervisor, startDate, endDate);
        this.secondSupervisor = secondSupervisor;
        
        // Additional validation for UR internship
        validateURInternship();
    }
    
    private void validateURInternship() {
        // Check that student is from UR
        if (!getStudent().getUniversity().equals("UR")) {
            throw new IllegalArgumentException("Student must be from UR for UR internship");
        }
        
        // Check that internship is between 2 and 6 months
        long months = ChronoUnit.MONTHS.between(getStartDate(), getEndDate());
        if (months < 2 || months > 6) {
            throw new IllegalArgumentException("UR internship must be between 2 and 6 months");
        }
    }
    
    @Override
    public void assignSupervisor() {
        System.out.println("UR Primary Supervisor assigned: " + getSupervisor().getFullName());
        addProgressNote("Primary Supervisor assigned: " + getSupervisor().getFullName());
        
        if (secondSupervisor != null) {
            System.out.println("UR Secondary Supervisor assigned: " + secondSupervisor.getFullName());
            addProgressNote("Secondary Supervisor assigned: " + secondSupervisor.getFullName());
        }
    }
    
    @Override
    public void trackProgress() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter progress note for UR internship " + getInternshipId() + ":");
        String note = scanner.nextLine();
        addProgressNote(note);
        System.out.println("Progress note added.");
    }
    
    @Override
    public String generateReport() {
        StringBuilder report = new StringBuilder(getBaseReport());
        report.append("\nUR SPECIFIC INFORMATION:\n");
        report.append("- Primary Supervisor: ").append(getSupervisor().getFullName()).append("\n");
        
        if (secondSupervisor != null) {
            report.append("- Secondary Supervisor: ").append(secondSupervisor.getFullName()).append("\n");
        } else {
            report.append("- No secondary supervisor assigned\n");
        }
        
        report.append("- UR internship duration: ").append(ChronoUnit.MONTHS.between(getStartDate(), getEndDate())).append(" months\n");
        
        return report.toString();
    }
    
    public Supervisor getSecondSupervisor() {
        return secondSupervisor;
    }
    
    @Override
    public String toString() {
        String baseInfo = super.toString() + "\nType: UR Internship";
        if (secondSupervisor != null) {
            baseInfo += "\nSecondary Supervisor: " + secondSupervisor.getFullName();
        }
        return baseInfo;
    }
}

class AUCAInternship extends Internship {
    private int communityServiceHours;
    private int completedCommunityHours;
    
    public AUCAInternship(String internshipId, Student student, String companyName, Supervisor supervisor,
                          LocalDate startDate, LocalDate endDate, int communityServiceHours) {
        super(internshipId, student, companyName, supervisor, startDate, endDate);
        this.communityServiceHours = communityServiceHours;
        this.completedCommunityHours = 0;
        
        // Additional validation for AUCA internship
        validateAUCAInternship();
    }
    
    private void validateAUCAInternship() {
        // Check that student is from AUCA
        if (!getStudent().getUniversity().equals("AUCA")) {
            throw new IllegalArgumentException("Student must be from AUCA for AUCA internship");
        }
        
        // Check that community service hours are positive
        if (communityServiceHours <= 0) {
            throw new IllegalArgumentException("Community service hours must be positive");
        }
    }
    
    @Override
    public void assignSupervisor() {
        System.out.println("AUCA Supervisor assigned: " + getSupervisor().getFullName());
        addProgressNote("Supervisor assigned: " + getSupervisor().getFullName());
    }
    
    @Override
    public void trackProgress() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter progress note for AUCA internship " + getInternshipId() + ":");
        String note = scanner.nextLine();
        
        System.out.println("Enter community service hours completed this week:");
        int hours = ValidationUtil.getValidInt("Hours:", 0, 40);
        
        completedCommunityHours += hours;
        addProgressNote(note + " (Community hours: " + hours + ", Total: " + completedCommunityHours + "/" + communityServiceHours + ")");
        
        System.out.println("Progress note and community hours updated.");
    }
    
    @Override
    public String generateReport() {
        StringBuilder report = new StringBuilder(getBaseReport());
        report.append("\nAUCA SPECIFIC INFORMATION:\n");
        report.append("- Required community service hours: ").append(communityServiceHours).append("\n");
        report.append("- Completed community service hours: ").append(completedCommunityHours).append("\n");
        report.append("- Progress: ").append(String.format("%.1f%%", (completedCommunityHours * 100.0 / communityServiceHours))).append("\n");
        
        return report.toString();
    }
    
    public int getCommunityServiceHours() {
        return communityServiceHours;
    }
    
    public int getCompletedCommunityHours() {
        return completedCommunityHours;
    }
    
    public void addCommunityHours(int hours) {
        if (hours > 0) {
            this.completedCommunityHours += hours;
            addProgressNote("Added " + hours + " community service hours. Total: " + completedCommunityHours + "/" + communityServiceHours);
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + "\nType: AUCA Internship" +
               "\nCommunity Service Hours: " + completedCommunityHours + "/" + communityServiceHours;
    }
}

class UKInternship extends Internship {
    private Supervisor universitySupervisor;
    private String englishProficiencyLevel;
    
    public UKInternship(String internshipId, Student student, String companyName, Supervisor companySupervisor,
                        LocalDate startDate, LocalDate endDate, Supervisor universitySupervisor, String englishProficiencyLevel) {
        super(internshipId, student, companyName, companySupervisor, startDate, endDate);
        this.universitySupervisor = universitySupervisor;
        this.englishProficiencyLevel = englishProficiencyLevel;
        
        // Additional validation for UK internship
        validateUKInternship();
    }
    
    private void validateUKInternship() {
        // Check that student is from UK
        if (!getStudent().getUniversity().equals("UK")) {
            throw new IllegalArgumentException("Student must be from UK for UK internship");
        }
        
        // Check that university supervisor is not null
        if (universitySupervisor == null) {
            throw new IllegalArgumentException("UK internship requires a university supervisor");
        }
        
        // Check that English proficiency level is valid
        if (englishProficiencyLevel == null || !isValidProficiencyLevel(englishProficiencyLevel)) {
            throw new IllegalArgumentException("English proficiency level must be A1, A2, B1, B2, C1, or C2");
        }
    }
    
    private boolean isValidProficiencyLevel(String level) {
        return level.equals("A1") || level.equals("A2") || level.equals("B1") || 
               level.equals("B2") || level.equals("C1") || level.equals("C2");
    }
    
    @Override
    public void assignSupervisor() {
        System.out.println("UK Company Supervisor assigned: " + getSupervisor().getFullName());
        System.out.println("UK University Supervisor assigned: " + universitySupervisor.getFullName());
        
        addProgressNote("Company Supervisor assigned: " + getSupervisor().getFullName());
        addProgressNote("University Supervisor assigned: " + universitySupervisor.getFullName());
    }
    
    @Override
    public void trackProgress() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter progress note for UK internship " + getInternshipId() + ":");
        String note = scanner.nextLine();
        
        System.out.println("Supervisor providing feedback (1. Company Supervisor, 2. University Supervisor):");
        int supervisorChoice = ValidationUtil.getValidInt("Choice:", 1, 2);
        
        String supervisorName = (supervisorChoice == 1) ? getSupervisor().getFullName() : universitySupervisor.getFullName();
        addProgressNote(note + " (Feedback from: " + supervisorName + ")");
        
        System.out.println("Progress note added.");
    }
    
    @Override
    public String generateReport() {
        StringBuilder report = new StringBuilder(getBaseReport());
        report.append("\nUK SPECIFIC INFORMATION:\n");
        report.append("- Company Supervisor: ").append(getSupervisor().getFullName()).append("\n");
        report.append("- University Supervisor: ").append(universitySupervisor.getFullName()).append("\n");
        report.append("- English Proficiency Level: ").append(englishProficiencyLevel).append("\n");
        
        return report.toString();
    }
    
    public Supervisor getUniversitySupervisor() {
        return universitySupervisor;
    }
    
    public String getEnglishProficiencyLevel() {
        return englishProficiencyLevel;
    }
    
    @Override
    public String toString() {
        return super.toString() + "\nType: UK Internship" +
               "\nUniversity Supervisor: " + universitySupervisor.getFullName() +
               "\nEnglish Proficiency Level: " + englishProficiencyLevel;
    }
}

class RemoteInternship extends Internship {
    private String communicationPlatform;
    private List<String> communicationLog;
    
    public RemoteInternship(String internshipId, Student student, String companyName, Supervisor supervisor,
                           LocalDate startDate, LocalDate endDate, String communicationPlatform) {
        super(internshipId, student, companyName, supervisor, startDate, endDate);
        this.communicationPlatform = communicationPlatform;
        this.communicationLog = new ArrayList<>();
        
        // No additional validation needed for Remote internship as it can be for any university
    }
    
    @Override
    public void assignSupervisor() {
        System.out.println("Remote Supervisor assigned: " + getSupervisor().getFullName());
        addProgressNote("Supervisor assigned: " + getSupervisor().getFullName());
        logCommunication("Initial supervisor assignment via " + communicationPlatform);
    }
    
    @Override
    public void trackProgress() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter progress note for Remote internship " + getInternshipId() + ":");
        String note = scanner.nextLine();
        
        System.out.println("Enter communication details:");
        String communication = scanner.nextLine();
        
        addProgressNote(note);
        logCommunication(communication);
        
        System.out.println("Progress note and communication log updated.");
    }
    
    public void logCommunication(String message) {
        if (message != null && !message.trim().isEmpty()) {
            String logEntry = LocalDate.now() + " - " + message + " (via " + communicationPlatform + ")";
            communicationLog.add(logEntry);
        }
    }
    
    @Override
    public String generateReport() {
        StringBuilder report = new StringBuilder(getBaseReport());
        report.append("\nREMOTE INTERNSHIP SPECIFIC INFORMATION:\n");
        report.append("- Communication Platform: ").append(communicationPlatform).append("\n");
        report.append("- Communication Log:\n");
        
        if (communicationLog.isEmpty()) {
            report.append("  - No communication logged yet\n");
        } else {
            for (String log : communicationLog) {
                report.append("  - ").append(log).append("\n");
            }
        }
        
        return report.toString();
    }
    
    public String getCommunicationPlatform() {
        return communicationPlatform;
    }
    
    public List<String> getCommunicationLog() {
        return communicationLog;
    }
    
    @Override
    public String toString() {
        return super.toString() + "\nType: Remote Internship" +
               "\nCommunication Platform: " + communicationPlatform +
               "\nCommunication Log Entries: " + communicationLog.size();
    }
}

interface Reportable {
    String generateDetailedReport();
    void exportReport(String format);
}

class ReportableInternship implements Reportable {
    private Internship internship;
    
    public ReportableInternship(Internship internship) {
        this.internship = internship;
    }
    
    @Override
    public String generateDetailedReport() {
        StringBuilder detailedReport = new StringBuilder(internship.generateReport());
        detailedReport.append("\nADDITIONAL REPORT INFORMATION:\n");
        detailedReport.append("- Report generated on: ").append(LocalDate.now()).append("\n");
        detailedReport.append("- Total duration: ").append(ChronoUnit.DAYS.between(internship.getStartDate(), internship.getEndDate())).append(" days\n");
        
        return detailedReport.toString();
    }
    
    @Override
    public void exportReport(String format) {
        System.out.println("Exporting report in " + format + " format...");
        // In a real application, this would write the report to a file
        System.out.println("Report exported successfully.");
    }
}







