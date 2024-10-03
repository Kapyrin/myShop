package kapyrin.myshop.exception;

public class LoadPropertiesException extends RuntimeException {
    public LoadPropertiesException(String message) {
        super(message);
    }

    public LoadPropertiesException(String message, Throwable cause) {
        super(message, cause);
    }
}
