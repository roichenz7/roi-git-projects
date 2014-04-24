package data;

import enums.Quality;
import org.jsoup.nodes.Element;

public abstract class ResultData {

    protected String tvShowName;
    protected int season;
    protected int episode;
    protected Quality quality;

    protected int seeds;
    protected int peers;

    protected String origin;
    protected boolean isProper;

    protected String downloadLink;

    public ResultData(Element source) {
        initialize(source);
    }

    protected abstract void initialize(Element source);

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

    public int getSeeds() {
        return seeds;
    }

    public int getPeers() {
        return peers;
    }

    public String getOrigin() {
        return origin;
    }

    public boolean isProper() {
        return isProper;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    @Override
    public String toString() {
        return String.format("%s.S%02dE%02d.%s", tvShowName, season, episode, quality);
    }
}
