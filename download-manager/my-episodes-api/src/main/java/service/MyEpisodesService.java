package service;

public interface MyEpisodesService {

    void markAsAcquired(int showId, int season, int episode);

    void markAsWatched(int showId, int season, int episode);
}
