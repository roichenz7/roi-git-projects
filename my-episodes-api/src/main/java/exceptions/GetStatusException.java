package exceptions;

public class GetStatusException extends RuntimeException {

    public GetStatusException(Throwable cause) {
        super("Failed to get status", cause);
    }

    public GetStatusException(String message) {
        super("Failed to get status. " + message);
    }
}
