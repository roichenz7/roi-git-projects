package data;

public class EpisodeData {

    private TvShowData tvShowData;
    private int season;
    private int episode;

    public EpisodeData(TvShowData tvShowData, int season, int episode) {
        this.tvShowData = tvShowData;
        this.season = season;
        this.episode = episode;
    }

    public TvShowData getTvShowData() {
        return tvShowData;
    }

    public int getSeason() {
        return season;
    }

    public int getEpisode() {
        return episode;
    }
}
