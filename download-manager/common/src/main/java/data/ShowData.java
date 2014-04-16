package data;

public class ShowData {

    public ShowData() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSeason() {
        return season;
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
        ShowData showData = new ShowData();
        String title = "";
        String[] tokens = filename.split("[.]");
        for (String temp : tokens) {
            if (temp.matches("S[0-9][0-9](E[0-9][0-9])+")) { // found season & episode/s
                char c1 = temp.charAt(1);
                char c2 = temp.charAt(2);
                if (c1 == '0')
                    showData.setSeason("Season " + c2);
                else
                    showData.setSeason("Season " + c1 + c2);

                showData.setTitle(title.trim());
                return showData;
            } else {
                title = title + " " + temp;
            }
        }

        return showData;
    }

    private String title;
    private String season;
}
