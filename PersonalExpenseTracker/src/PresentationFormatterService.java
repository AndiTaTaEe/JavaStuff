import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Month;
import java.util.Map;
import java.util.Optional;


public class PresentationFormatterService {

    public void generatePresentationFile(MonthlyReport monthlyReport) throws StorageException{
        if (monthlyReport == null){
            throw new IllegalArgumentException("Monthly report cannot be null");
        }
        int year = monthlyReport.period().getYear();
        Month month = monthlyReport.period().getMonth();
        // generate the file - report_year_month.md / .txt
        final String nameFile = "report_" + year + "_" + month + ".md";
        Path presentationFile = Path.of(nameFile);

        // open a writer in order to write in the file
        try (BufferedWriter writer = Files.newBufferedWriter(presentationFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)){
            writer.write("# Monthly Expense Summary - " + month + " " + year + "\n");
            writer.write(String.format("* Amount spent: %.2f\n", monthlyReport.totalSpent()));
            writer.write("* Total number of transactions: " + monthlyReport.transactionCount() + "\n");
            writer.write(String.format("* Average spend per transaction: %.2f\n", monthlyReport.averageTransaction()));

            // building the breakdown table
            // write the table header
            writer.write("\n### Category Breakdown\n\n");
            writer.write("| Category | Total amount ($) |\n");
            writer.write("|---|---|\n");

            //write each category row
            for(Map.Entry<Category, Double> entry : monthlyReport.categoryTotals().entrySet()){
                writer.write(String.format("| %s | $%.2f |\n", entry.getKey().name(), entry.getValue()));
            }
            // write the top category
            writer.newLine();
            Optional<Category> topCategoryOpt = monthlyReport.topCategory();
            if (topCategoryOpt.isPresent()){
                Category topCategory = topCategoryOpt.get();
                double topAmount = monthlyReport.categoryTotals().getOrDefault(topCategory, 0.0);
                writer.write(String.format("> **Highest spending category:** %s ($%.2f)\n", topCategory.name(), topAmount));
            } else {
                writer.write("> *No expenses recorded for this time period.*\n");
            }
        } catch (IOException e){
            throw new StorageException("Failed to write monthly report to: " + presentationFile, e);
        }
    }
}
