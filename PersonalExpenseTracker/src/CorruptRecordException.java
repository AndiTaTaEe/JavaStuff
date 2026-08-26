public class CorruptRecordException extends Exception {
    private final int lineNumber;
    private final String rawString;
    public CorruptRecordException(String message, int lineNumber, String rawString) {
        super(String.format("Error on line %d: %s [Raw string: \"%s\"]", lineNumber, message, rawString));
        this.lineNumber = lineNumber;
        this.rawString = rawString;
    }

    public CorruptRecordException(String message, int lineNumber, String rawString, Throwable cause) {
        super(String.format("Error on line %d: %s [Raw string: \"%s\"]", lineNumber, message, rawString), cause);
        this.lineNumber = lineNumber;
        this.rawString = rawString;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getRawString() {
        return rawString;
    }
}
