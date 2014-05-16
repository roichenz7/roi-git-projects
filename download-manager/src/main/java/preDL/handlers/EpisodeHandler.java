package preDL.handlers;

import data.EpisodeData;
import enums.Quality;

public interface EpisodeHandler {

    /**
     * Handles given episode
     *
     * @param episode episode
     * @param quality quality
     * @param downloadPath download path
     * @return true if handling succeeded, false otherwise
     */
    boolean handle(EpisodeData episode, Quality quality, String downloadPath);
}
