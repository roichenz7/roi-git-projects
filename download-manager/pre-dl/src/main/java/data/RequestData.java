package data;

import enums.Quality;

public class RequestData {

    private String tvShowName;
    private int season;
    private int episode;
    private Quality quality;

    public RequestData(String tvShowName, int season, int episode, Quality quality) {
        this.tvShowName = tvShowName;
        this.season = season;
        this.episode = episode;
        this.quality = quality;
    }

    public String getTvShowName() {
        return tvShowName;
    }

    public int getSeason() {
        return season;
    }

    public int getEpisode() {
        return episode;
    }

    public Quality getQuality() {
        return quality;
    }

    @Override
    public String toString() {
        return String.format("%s S%02dE%02d %s", tvShowName, season, episode, quality);
    }
}
