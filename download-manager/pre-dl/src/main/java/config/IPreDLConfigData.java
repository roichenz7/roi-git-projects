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

    /**
     * @param id tv show id
     * @return tv show quality
     */
    default Quality getTvShowQuality(int id) {
        return specialShows().stream()
                .filter(x -> x.getId() == id)
                .map(x -> Quality.fromString(x.getQuality()))
                .findFirst()
                .orElse(defaultQuality());
    }
}
