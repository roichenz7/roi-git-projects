package exceptions;

public class ResultNotFoundException extends RuntimeException {

    public ResultNotFoundException(String message) {
        super("Result not found: " + message);
    }
}
