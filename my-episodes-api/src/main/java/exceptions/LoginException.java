package exceptions;

public class LoginException extends RuntimeException {

    public LoginException(String username, Throwable cause) {
        super(String.format("Login of user: %s failed", username), cause);
    }

    public LoginException(String username, String message) {
        super(String.format("Login of user: %s failed. %s", username, message));
    }
}
