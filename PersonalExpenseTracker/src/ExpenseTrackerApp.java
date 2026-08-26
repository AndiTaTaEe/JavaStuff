import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ExpenseTrackerApp {
    private final Scanner scanner;
    private final ExpenseStorage storage;
    private final ExpenseAnalytics analytics;
    private final PresentationFormatterService formatter;

    public ExpenseTrackerApp(String filePath){
        this.scanner = new Scanner(System.in);
        this.storage = new ExpenseStorage(filePath);
        this.analytics = new ExpenseAnalytics();
        this.formatter = new PresentationFormatterService();
    }

    public static void main(String[] args) {
        ExpenseTrackerApp app = new ExpenseTrackerApp("data/expenses.csv");
        app.run();
    }

    public void run(){
        boolean running = true;
        while (running){
            System.out.println("\n--- PERSONAL EXPENSE TRACKER ---");
            System.out.println("1. Add new expense");
            System.out.println("2. View all logged expenses");
            System.out.println("3. Generate monthly report (.md file)");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");

            String input = scanner.nextLine().trim();
            switch(input) {
                case "1" -> handleAddExpense();
                case "2" -> handleViewLoggedExpenses();
                case "3" -> handleGenerateMonthlyReport();
                case "4" -> {
                    System.out.println("Exiting application...");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1-4.");
            }
        }
    }

    private double readPositiveDouble(String prompt){
        while (true){
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value > 0){
                    return value;
                }
                System.out.println("Amount must be strictly greater than 0.");
            } catch (NumberFormatException e){
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }
    }

    private Category readCategory(){
        while (true){
            System.out.print("Enter category (FOOD, TRANSPORT, HOUSING, UTILITIES, ENTERTAINMENT, MISC): ");
            String input = scanner.nextLine();
            Optional<Category> categoryOpt = Category.fromString(input);
            if (categoryOpt.isPresent()){
                return categoryOpt.get();
            }
            System.out.println("Invalid category name. Please choose from the listed options.");
        }
    }

    private String readNonEmptyString(String prompt){
        while (true){
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()){
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    private int readIntRange (String prompt, int min, int max){
        while (true){
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max){
                    return value;
                }
                System.out.printf("Please enter a value between %d and %d.\n", min, max);
            } catch (NumberFormatException e){
                System.out.println("Invalid input. Please enter a whole number");
            }
        }
    }

    private void handleAddExpense(){
        System.out.println("\n--- Add new expense ---");
        double amount = readPositiveDouble("Enter amount: ");
        Category category = readCategory();
        String description = readNonEmptyString("Enter description: ");
        // instantiate the record
        Expense expense = new Expense(amount, category, description);
        try {
            storage.appendExpense(expense);
            System.out.println(" Expense added successfully! ");
        } catch (StorageException e) {
            System.out.println("Error: Unable to save expense to disk. Please check storage permissions.");
        }
    }

    private void handleViewLoggedExpenses(){
        System.out.println("\n--- All logged expenses ---");
        try {
            List<Expense> loggedExpenses = storage.parsingExpenses();
            if (loggedExpenses.isEmpty()){
                System.out.println("There are no logged expenses.");
                return;
            }
            // table header
            System.out.printf("%-12s | %-15s | %-10s | %s\n", "Date", "Category", "Amount", "Description");
            System.out.println("-".repeat(60));
            for (Expense expense : loggedExpenses) {
                System.out.printf("%-12s | %-15s | $%-9.2f | %s\n",
                        expense.date(), expense.category(), expense.amount(), expense.description());
            }

        } catch (CorruptRecordException e){
            System.out.println("Corrupted data found in CSV file: " + e.getMessage());
        } catch (StorageException e){
            System.out.println("Failed to read storage file: " + e.getMessage());
        }
    }

    private void handleGenerateMonthlyReport(){
        System.out.println("\n--- Generate monthly report ---");
        int year = readIntRange("Enter year (e.g.: 2026): ", 2000, 2100);
        int month = readIntRange("Enter month (1-12): ", 1, 12);
        YearMonth period = YearMonth.of(year, month);

        try {
            List<Expense> allExpenses = storage.parsingExpenses();
            MonthlyReport report = analytics.generateMonthlyReport(allExpenses, period);
            formatter.generatePresentationFile(report);
            System.out.println("Report generated successfully as report_" + year + "_" + month + ".md");
        } catch (StorageException | CorruptRecordException e){
            System.out.println("Error generating report: " + e.getMessage());
        }
    }
}