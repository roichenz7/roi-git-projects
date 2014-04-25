package data;

public class EpisodeData {

    private int tvShowId;
    private String tvShowName;
    private int season;
    private int episode;

    public EpisodeData(int tvShowId, String tvShowName, int season, int episode) {
        this.tvShowId = tvShowId;
        this.tvShowName = tvShowName;
        this.season = season;
        this.episode = episode;
    }

    /**
     * @return tv show id
     */
    public int getTvShowId() {
        return tvShowId;
    }

    /**
     * @return tv show name
     */
    public String getTvShowName() {
        return tvShowName;
    }

    /**
     * @return episode's season
     */
    public int getSeason() {
        return season;
    }

    /**
     * @return episode's number
     */
    public int getEpisode() {
        return episode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EpisodeData that = (EpisodeData) o;
        return tvShowId == that.tvShowId && season == that.season && episode == that.episode;

    }

    @Override
    public int hashCode() {
        int result = tvShowId;
        result = 31 * result + season;
        result = 31 * result + episode;
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s (%d) S%02dE%02d", tvShowName, tvShowId, season, episode);
    }
}
