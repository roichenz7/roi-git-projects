package service;

public interface MyEpisodesService {

    /**
     * Marks given episode as acquired
     *
     * @param showId show id
     * @param season season number
     * @param episode episode number
     */
    void markAsAcquired(int showId, int season, int episode);

    /**
     * Marks given episode as watched
     *
     * @param showId show id
     * @param season season number
     * @param episode episode number
     */
    void markAsWatched(int showId, int season, int episode);
}
