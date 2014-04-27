package exceptions;

public class GetMyShowsException extends RuntimeException {

    public GetMyShowsException(Throwable cause) {
        super("Failed to get my shows", cause);
    }

    public GetMyShowsException(String message) {
        super("Failed to get my shows. " + message);
    }
}
