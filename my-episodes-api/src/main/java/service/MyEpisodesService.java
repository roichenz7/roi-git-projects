package service;

import data.TvShowData;

import java.util.Collection;

public interface MyEpisodesService {

    /**
     * @return tv shows status
     */
    Collection<TvShowData> getStatus();

    /**
     * Marks given episode as acquired
     *
     * @param showId  show id
     * @param season  season number
     * @param episode episode number
     */
    void markAsAcquired(int showId, int season, int episode);

    /**
     * Marks given episode as acquired
     *
     * @param showName show name
     * @param season   season number
     * @param episode  episode number
     */
    void markAsAcquired(String showName, int season, int episode);

    /**
     * Marks given episode as watched
     *
     * @param showId  show id
     * @param season  season number
     * @param episode episode number
     */
    void markAsWatched(int showId, int season, int episode);

    /**
     * Marks given episode as watched
     *
     * @param showName show name
     * @param season   season number
     * @param episode  episode number
     */
    void markAsWatched(String showName, int season, int episode);
}
