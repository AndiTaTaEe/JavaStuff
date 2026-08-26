import java.time.YearMonth;
import java.util.Map;
import java.util.Optional;

public record MonthlyReport(YearMonth period, double totalSpent, int transactionCount, double averageTransaction, Map<Category, Double> categoryTotals, Optional<Category> topCategory) {
}
