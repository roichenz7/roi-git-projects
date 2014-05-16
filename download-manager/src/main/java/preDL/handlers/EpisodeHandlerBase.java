package preDL.handlers;

import data.EpisodeData;
import enums.Quality;

public abstract class EpisodeHandlerBase implements EpisodeHandler {

    private EpisodeHandler next;

    @Override
    public final boolean handle(EpisodeData episode, Quality quality, String downloadPath) {
        if (doHandle(episode, quality, downloadPath))
            return true;

        return next != null && next.handle(episode, quality, downloadPath);
    }

    /**
     * Sets next episode handler
     *
     * @param next next episode handler
     * @return next episode handler
     */
    public EpisodeHandler setNext(EpisodeHandler next) {
        this.next = next;
        return next;
    }

    /**
     * Handles given episode data
     *
     * @param episode episode
     * @param quality quality
     * @param downloadPath download path
     * @return true if handling succeeded, false otherwise
     */
    protected abstract boolean doHandle(EpisodeData episode, Quality quality, String downloadPath);
}
