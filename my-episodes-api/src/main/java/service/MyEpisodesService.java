package service;

import data.EpisodeData;
import data.TvShowData;

import java.util.Collection;
import java.util.List;

public interface MyEpisodesService {

    /**
     * @return tv shows status
     */
    List<TvShowData> getStatus();

    /**
     * @return un-acquired episodes
     */
    Collection<EpisodeData> getUnAcquiredEpisodes();

    /**
     * @return un-seen episodes
     */
    Collection<EpisodeData> getUnSeenEpisodes();

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
