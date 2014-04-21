package data;

public class ShowData {

    private String title;
    private String season;
    private int seasonNumber;
    private int episodeNumber;

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

    public void setSeason(String season) {
        this.season = season;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public boolean isProper() {
        return isProper;
    }

    public void setProper(boolean isProper) {
        this.isProper = isProper;
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
        // TODO: parse better using pattern matching
        ShowData showData = new ShowData();
        String title = "";

        if (filename.matches("PROPER"))
            showData.setProper(true);

        // TODO; set origin

        String[] tokens = filename.split(regex);
        for (String temp : tokens) {
            if (temp.matches("S[0-9][0-9](E[0-9][0-9])+")) { // found season & episode/s
                char c1 = temp.charAt(1);
                char c2 = temp.charAt(2);
                if (c1 == '0') {
                    showData.setSeason("Season " + c2);
                    showData.seasonNumber = Integer.parseInt(String.valueOf(c2));
                }
                else {
                    showData.setSeason("Season " + c1 + c2);
                    showData.seasonNumber = Integer.parseInt(String.valueOf(c1) + String.valueOf(c2));
                }

                // TODO: handle multiple episodes...
                int index = temp.indexOf("E");
                char c3 = temp.charAt(index + 1);
                char c4 = temp.charAt(index + 2);
                if (c3 == '0') {
                    showData.episodeNumber = Integer.parseInt(String.valueOf(c4));
                }
                else {
                    showData.episodeNumber = Integer.parseInt(String.valueOf(c3) + String.valueOf(c4));
                }

                showData.setTitle(title.trim());
                return showData;
            } else {
                title = title + " " + temp;
            }
        }

        return showData;
    }
}
