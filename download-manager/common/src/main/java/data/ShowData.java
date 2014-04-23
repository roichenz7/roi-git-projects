package data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShowData {

    private String title;
    private String season;
    private int seasonNumber;
    private int episodeNumber;

    private String quality;

    private String origin;
    private boolean isProper;

    public ShowData() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeason() {
        return season;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String getQuality() {
        return quality;
    }

    public String getOrigin() {
        return origin;
    }

    public boolean isProper() {
        return isProper;
    }

    public boolean isEmpty() {
        return title == null && season == null;
    }

    /**
     * Creates show data from given filename
     *
     * @param filename: filename to parse
     * @return empty data if unsuccessful, valid data otherwise
     */
    public static ShowData fromFilename(String filename) {
        return fromFilename(filename, "[.]");
    }

    /**
     * Creates show data from given filename
     *
     * @param filename: filename to parse
     * @param regex: regex
     * @return empty data if unsuccessful, valid data otherwise
     */
    public static ShowData fromFilename(String filename, String regex) {
        ShowData showData = new ShowData();

        Matcher matcher = Pattern.compile("(.*)(S[0-9][0-9](E[0-9][0-9])+)(.*)").matcher(filename);
        if (matcher.find()) {
            showData.title = matcher.group(1).replaceAll(regex, " ").trim();

            String seasonAndEpisodes = matcher.group(2);

            String season = seasonAndEpisodes.replaceAll("S", "").replaceAll("E.*", "").replaceAll("0", "");
            showData.season = "Season " + season;
            showData.seasonNumber = Integer.parseInt(season);

            String episode = seasonAndEpisodes.replaceAll("S.*E", ""); // TODO: handle multiple episodes
            showData.episodeNumber = Integer.parseInt(episode);
        }

        matcher = Pattern.compile(".*(720p|1080p).*").matcher(filename);
        if (matcher.find())
            showData.quality = matcher.group(1);
        else
            showData.quality = "";

        matcher = Pattern.compile(".*(DIMENSION|2HD|KILLERS|REMARKABLE|PublicHD|NTb|LOL|AFG|FoV).*").matcher(filename); // TODO: better
        if (matcher.find())
            showData.origin = matcher.group(1);

        if (filename.matches(".*PROPER.*"))
            showData.isProper = true;

        return showData;
    }
}