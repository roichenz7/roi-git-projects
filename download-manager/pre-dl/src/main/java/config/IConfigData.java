package config;

import data.TvShowData;

import java.util.Collection;

public interface IConfigData {

    /**
     * @return download directory
     */
    String downloadDir();

    /**
     * @return ignored shows
     */
    Collection<TvShowData> ignoredShows();

    /**
     * Parses given configuration file
     *
     * @param filename configuration file
     * @return true if successful, false otherwise
     */
    boolean parse(String filename);
}
