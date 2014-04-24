package exceptions;

public class GetStatusException extends RuntimeException {

    public GetStatusException() {
        super("Failed to get status");
    }

    public GetStatusException(Throwable cause) {
        super("Failed to get status", cause);
    }
}
