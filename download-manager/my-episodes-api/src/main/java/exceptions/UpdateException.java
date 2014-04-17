package exceptions;

public class UpdateException extends RuntimeException {

    public UpdateException(int showId, int season, int episode, Throwable cause) {
        super(String.format("Update of show %d, season %d, episode %d failed", showId, season, episode), cause);
    }
}
