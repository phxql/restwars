package restwars;

public class UnrecoverableException extends RuntimeException {
    public UnrecoverableException(String message, Throwable cause) {
        super(message, cause);
    }
}
