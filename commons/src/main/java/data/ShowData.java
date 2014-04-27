package data;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShowData {

    private int id = -1;
    private String title;
    private String season;
    private int seasonNumber;
    private int episodeNumber;

    private String quality;

    private String origin;
    private boolean isProper;

    public ShowData() {
    }

    public ShowData(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        id = Integer.parseInt(attributes.getNamedItem("id").getNodeValue());
        title = attributes.getNamedItem("name").getNodeValue();
        quality = attributes.getNamedItem("quality").getNodeValue();
    }

    public int getId() {
        return id;
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
     * @param filename filename to parse
     * @return empty data if unsuccessful, valid data otherwise
     */
    public static ShowData fromFilename(String filename) {
        return fromFilename(filename, "[.]");
    }

    /**
     * Creates show data from given filename
     *
     * @param filename filename to parse
     * @param titleSplitRegex titleSplitRegex
     * @return empty data if unsuccessful, valid data otherwise
     */
    public static ShowData fromFilename(String filename, String titleSplitRegex) {
        ShowData showData = new ShowData();

        Matcher matcher = Pattern.compile("(.*)(S[0-9][0-9]x?(E[0-9][0-9])+)(.*)", Pattern.CASE_INSENSITIVE)
                .matcher(filename);

        if (matcher.find()) {
            showData.title = matcher.group(1)
                    .replaceAll(titleSplitRegex, " ")
                    .trim();

            String seasonAndEpisodes = matcher.group(2);

            String season = seasonAndEpisodes.replaceAll("[S|s]", "")
                    .replaceAll("x?[E|e].*", "")
                    .replaceAll("0", "");

            showData.season = "Season " + season;
            showData.seasonNumber = Integer.parseInt(season);

            String episode = Pattern.compile("S.*E", Pattern.CASE_INSENSITIVE)
                    .matcher(seasonAndEpisodes)
                    .replaceAll(""); // TODO: handle multiple episodes

            showData.episodeNumber = Integer.parseInt(episode);
        }

        matcher = Pattern.compile(".*(720p|1080p).*").matcher(filename);
        if (matcher.find())
            showData.quality = matcher.group(1);
        else
            showData.quality = "";

        matcher = Pattern.compile(".*(DIMENSION|2HD|KILLERS|REMARKABLE|PublicHD|NTb|LOL|AFG|FoV).*", Pattern.CASE_INSENSITIVE)
                .matcher(filename); // TODO: better

        if (matcher.find())
            showData.origin = matcher.group(1);
        else
            showData.origin = "";

        matcher = Pattern.compile(".*(PROPER).*", Pattern.CASE_INSENSITIVE)
                .matcher(filename);

        if (matcher.find())
            showData.isProper = true;

        return showData;
    }

    @Override
    public String toString() {
        return String.format("%s (%d) (%s)", title, id, quality);
    }
}
