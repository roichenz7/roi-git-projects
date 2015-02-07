package data;

import exceptions.EpisodeParseException;
import org.jsoup.nodes.Element;
import string.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EpisodeData implements Comparable<EpisodeData> {

    private int tvShowId;
    private String tvShowName;

    private int season;
    private int episode;

    private String episodeName;

    private Date airDate;

    public EpisodeData(Element element) {
        try {
            String[] guid = element.select("guid")
                    .get(0)
                    .text()
                    .split("-");

            tvShowId = Integer.parseInt(guid[0]);
            season = Integer.parseInt(guid[1]);
            episode = Integer.parseInt(guid[2]);

            String titleText = element.select("title")
                    .get(0)
                    .text();
            String[] title = titleText.substring(1, titleText.length() - 1)
                    .trim()
                    .split("\\]\\[");

            tvShowName = title[0].trim();
            episodeName = title[2].trim();
            String date = title[3].trim() + "-8-00";
            airDate = new SimpleDateFormat("dd-MMM-yyyy-HH-mm", Locale.ENGLISH).parse(date);
        } catch (Exception e) {
            throw new EpisodeParseException(e);
        }
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
     * @return sanitized tv show name (removes all non-word characters)
     */
    public String getSanitizedTvShowName() {
        return StringUtils.removeSpecialChars(tvShowName);
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

    /**
     * @return episode's name
     */
    public String getEpisodeName() {
        return episodeName;
    }

    /**
     * @return episode's air date
     */
    public Date getAirDate() {
        return airDate;
    }

    /**
     * @return true if this episode has aired, false otherwise
     */
    public boolean isAired() {
        return new Date().after(getAirDate());
    }

    @Override
    public int compareTo(EpisodeData o) {
        if (tvShowId != o.tvShowId)
            return tvShowName.compareTo(o.tvShowName);

        if (season != o.season)
            return season - o.season;

        return episode - o.episode;
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
        return String.format("%s (%d) - S%02dE%02d - [%s] - %s", tvShowName, tvShowId, season, episode, airDate, episodeName);
    }
}
