package kapyrin.myshop.exception;

public class LoadDriverException extends RuntimeException {
    public LoadDriverException(String message) {
        super(message);
    }

    public LoadDriverException(String message, Throwable cause) {
        super(message, cause);
    }
}
