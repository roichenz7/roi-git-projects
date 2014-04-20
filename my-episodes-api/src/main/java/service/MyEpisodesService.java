package service;

public interface MyEpisodesService {

    /**
     * Loads configuration from given filename
     *
     * @param filename configuration filename
     */
    void configure(String filename);

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
