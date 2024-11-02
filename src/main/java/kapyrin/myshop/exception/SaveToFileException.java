package kapyrin.myshop.exception;

public class SaveToFileException extends RuntimeException {
    public SaveToFileException(String message) {
        super(message);
    }

    public SaveToFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
