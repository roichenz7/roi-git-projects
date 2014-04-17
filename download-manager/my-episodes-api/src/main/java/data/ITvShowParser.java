package data;

import data.parser.IDataParser;

public interface ITvShowParser extends IDataParser<TvShowData> {

    /**
     * @param id show id
     * @return show data according to id
     */
    TvShowData getShowById(int id);

    /**
     * @param name show name
     * @return show data according to name
     */
    TvShowData getShowByName(String name);
}
