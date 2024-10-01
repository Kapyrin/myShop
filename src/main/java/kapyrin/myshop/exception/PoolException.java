package kapyrin.myshop.exception;

public class PoolException extends RuntimeException {
    public PoolException(String message) {
        super(message);
    }

    public PoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
