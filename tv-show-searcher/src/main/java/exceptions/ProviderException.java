package exceptions;

public class ProviderException extends RuntimeException {

    public ProviderException() {
        super("Failed to create provider");
    }
}
