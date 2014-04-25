package exceptions;

public class EpisodeParseException extends RuntimeException {

    public EpisodeParseException(Throwable cause) {
        super("Failed to parse episode data", cause);
    }
}
