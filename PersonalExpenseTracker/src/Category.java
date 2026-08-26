import java.util.Optional;

public enum Category {
    FOOD,
    TRANSPORT,
    HOUSING,
    UTILITIES,
    ENTERTAINMENT,
    MISC;

    public static Optional<Category> fromString(String value) {
        if (value == null || value.isBlank()){
            return Optional.empty();
        }
        String normalized = value.trim();
        for (Category category : Category.values()){
            if (category.name().equalsIgnoreCase(normalized)){
                return Optional.of(category);
            }
        }
        return Optional.empty();
    }
}
