package config;

public interface IConfigData {

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
     * @return mark as acquired flag value
     */
    boolean isMarkAsAcquired();

    /**
     * @return username
     */
    String username();

    /**
     * @return passwoed
     */
    String password();

    /**
     * Parses given configuration file
     *
     * @param filename configuration file
     * @return true if successful, false otherwise
     */
    boolean parse(String filename);
}
