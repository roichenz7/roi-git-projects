package config;

import data.ShowData;
import data.TvShowData;
import enums.Quality;

import java.util.List;

public interface IPreDLConfigData {

    /**
     * @return download directory
     */
    String downloadDir();

    /**
     * @return default quality
     */
    Quality defaultQuality();

    /**
     * @return ignored shows
     */
    List<TvShowData> ignoredShows();

    /**
     * @return special shows
     */
    List<ShowData> specialShows();

    /**
     * Parses given configuration file
     *
     * @param filename configuration file
     * @return true if successful, false otherwise
     */
    boolean parse(String filename);
}
