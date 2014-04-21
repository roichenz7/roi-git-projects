package data;

public class ResultData {

    private String tvShowName;
    private int season;
    private int episode;

    private int seeds;
    private int peers;

    private String origin;
    private boolean isProper;

    public ResultData(String source) {
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

    public int getSeeds() {
        return seeds;
    }

    public int getPeers() {
        return peers;
    }

    public boolean isProper() {
        return isProper;
    }
}
