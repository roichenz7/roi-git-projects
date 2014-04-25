package file;

public enum FileType {

    TORRENT("torrent"),
    SRT("srt");

    private final String type;

    FileType(String type) {
        this.type = type;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return getType();
    }
}
