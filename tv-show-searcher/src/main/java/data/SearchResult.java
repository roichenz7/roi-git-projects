package data;

import enums.Quality;
import org.jsoup.nodes.Element;

public abstract class SearchResult {

    protected String tvShowName;
    protected int season;
    protected int episode;
    protected Quality quality;

    protected String origin;
    protected boolean isProper;

    protected int seeds;
    protected int peers;

    protected String downloadLink;

    public SearchResult(Element source) {
        initialize(source);
    }

    /**
     * @return tv show name
     */
    public String getTvShowName() {
        return tvShowName;
    }

    /**
     * @return sanitized tv show name (removes all non-word characters)
     */
    public String getSanitizedTvShowName() {
        return tvShowName.replaceAll("\\W", " ");
    }

    /**
     * @return season
     */
    public int getSeason() {
        return season;
    }

    /**
     * @return episode
     */
    public int getEpisode() {
        return episode;
    }

    /**
     * @return quality
     */
    public Quality getQuality() {
        return quality;
    }

    /**
     * @return origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @return true if this result is marked as 'proper', false otherwise
     */
    public boolean isProper() {
        return isProper;
    }

    /**
     * @return number of seeds
     */
    public int getSeeds() {
        return seeds;
    }

    /**
     * @return number of peers
     */
    public int getPeers() {
        return peers;
    }

    /**
     * @return download link
     */
    public String getDownloadLink() {
        return downloadLink;
    }

    @Override
    public String toString() {
        return String.format("%s.S%02dE%02d.%s", tvShowName, season, episode, quality);
    }

    /**
     * Initializes this result from show data
     *
     * @param showData show data
     */
    protected void initialize(ShowData showData) {
        tvShowName = showData.getTitle();
        season = showData.getSeasonNumber();
        episode = showData.getEpisodeNumber();

        quality = Quality.fromString(showData.getQuality());
        origin = showData.getOrigin();
        isProper = showData.isProper();
    }

    /**
     * Initializes this result from source element
     *
     * @param source source element
     */
    protected abstract void initialize(Element source);
}
