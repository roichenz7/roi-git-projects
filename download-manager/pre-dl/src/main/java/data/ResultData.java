package data;

import enums.Quality;
import org.jsoup.nodes.Element;

public class ResultData {

    private String tvShowName;
    private int season;
    private int episode;
    private Quality quality;

    private int seeds;
    private int peers;

    private String origin;
    private boolean isProper;

    private String downloadLink;

    public ResultData(Element source) {
        // TODO: init fields
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
