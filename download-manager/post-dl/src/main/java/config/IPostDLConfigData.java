package config;

public interface IPostDLConfigData {

    /**
     * @return source directory
     */
    String sourceDir();

    /**
     * @return target directory
     */
    String targetDir();

    /**
     * @return tv shows directory
     */
    String tvShowsDir();

    /**
     * Parses given configuration file
     *
     * @param filename configuration file
     * @return true if successful, false otherwise
     */
    boolean parse(String filename);
}
