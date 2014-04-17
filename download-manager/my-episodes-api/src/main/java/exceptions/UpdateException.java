package exceptions;

public class UpdateException extends RuntimeException {

    public UpdateException(int showId, int season, int episode, Throwable cause) {
        super(String.format("Update of show %d, season %d, episode %d failed", showId, season, episode), cause);
    }

    public UpdateException(int showId, int season, int episode, String message) {
        super(String.format("Update of show %d, season %d, episode %d failed. %s", showId, season, episode, message));
    }

    public UpdateException(String showName, int season, int episode, String message) {
        super(String.format("Update of show %s, season %d, episode %d failed. %s", showName, season, episode, message));
    }
}
