package enums;

import data.ShowData;

public enum Quality {

    HD_720p("720p"),
    HD_1080p("1080p"),
    HD("HDTV"),
    SD(""),
    UNKNOWN("");

    private final String str;

    Quality(String str) {
        this.str = str;
    }

    public static Quality fromString(String value) {
        if (value == null || value.isEmpty())
            return Quality.UNKNOWN;
        else if (value.equalsIgnoreCase("720p"))
            return Quality.HD_720p;
        else if(value.equalsIgnoreCase("1080p"))
            return Quality.HD_1080p;
        else if(value.equalsIgnoreCase("HD"))
            return Quality.HD;
        else if(value.equalsIgnoreCase("SD"))
            return Quality.SD;
        else
            return Quality.UNKNOWN;
    }

    public static Quality fromShowData(ShowData showData) {
        return fromString(showData.getQuality());
    }

    @Override
    public String toString() {
        return str;
    }
}
