import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class ExpenseAnalytics {

    public MonthlyReport generateMonthlyReport(List<Expense> allExpenses, YearMonth targetPeriod){
        if (allExpenses == null || targetPeriod == null){
            throw new IllegalArgumentException("Expenses list and target month cannot be null");
        }

        // filtering the expenses that match the records for that month
        List<Expense> monthlyExpenses = new ArrayList<>();
        for (Expense expense : allExpenses){
            LocalDate date = expense.date();
            if (date.getYear() == targetPeriod.getYear() && date.getMonth() == targetPeriod.getMonth()){
                monthlyExpenses.add(expense);
            }
        }
        // checking if no transactions exist for that requested month
        if (monthlyExpenses.isEmpty()){
            return new MonthlyReport(targetPeriod, 0.0, 0, 0.0, new EnumMap<>(Category.class), Optional.empty());
        }
        // computing totals and grouping by category
        double totalSpent = 0.0;
        Map<Category, Double> categoryTotals = new EnumMap<>(Category.class); // storing cateogry as a key, and the amount spent on that cateogry a s a value
        for (Expense expense : monthlyExpenses){
            double amount = expense.amount();
            Category category = expense.category();
            totalSpent += amount; // accumulate total
            // accumulate per category
            double currentCategorySum = categoryTotals.getOrDefault(category, 0.0); // if it s the first time seeing that category, store the new amount directly
            categoryTotals.put(category, currentCategorySum + amount);
        }

        // identifying the top category
        Category topCategory = null;
        double maxSpent = -1.0;
        for (Map.Entry<Category, Double> entry : categoryTotals.entrySet()){
            if (entry.getValue() > maxSpent){
                maxSpent = entry.getValue();
                topCategory = entry.getKey();
            }
        }

        // average computation
        double average = totalSpent / monthlyExpenses.size();

        // return a new monthly report
        return new MonthlyReport(targetPeriod, totalSpent, monthlyExpenses.size(), average, categoryTotals, Optional.ofNullable(topCategory));

    }
}
