package enums;

public enum Quality {

    HD_720p("720p"),
    HD_1080p("1080p"),
    SD("");

    private String name;

    private Quality(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
