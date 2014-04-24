package enums;

public enum Quality {

    HD_720p("720p"),
    HD_1080p("1080p"),
    SD("");

    private String name;

    private Quality(String name) {
        this.name = name;
    }

    public static Quality fromString(String value) {
        if (value == null || value.isEmpty())
            return Quality.SD;
        else if (value.equalsIgnoreCase("720p"))
            return Quality.HD_720p;
        else if(value.equalsIgnoreCase("1080p"))
            return Quality.HD_1080p;
        else
            return Quality.SD;
    }

    @Override
    public String toString() {
        return name;
    }
}
