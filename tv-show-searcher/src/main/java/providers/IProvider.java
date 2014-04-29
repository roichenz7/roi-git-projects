package providers;

import data.RequestData;
import data.ResultData;
import enums.Quality;

import java.util.*;

public interface IProvider {

    /**
     * Accepted origins field
     */
    List<String> acceptedOrigins = new ArrayList<>();

    /**
     * @return provider name
     */
    String getName();

    /**
     * @return provider base url
     */
    String getBaseUrl();

    /**
     * Performs search according to requestData
     *
     * @param requestData request data
     * @return list of results
     */
    List<ResultData> search(RequestData requestData);

    /**
     * Performs search according to given parameters
     *
     * @param tvShowName tv show name
     * @param season season number
     * @param episode episode number
     * @param quality file quality
     * @return list of results
     */
    default List<ResultData> search(String tvShowName, int season, int episode, Quality quality) {
        return search(new RequestData(tvShowName, season, episode, quality));
    }

    /**
     * Returns best result from given results
     *
     * @param results results
     * @return best result
     */
    ResultData getBestResult(List<ResultData> results);

    /**
     * Sets accepted origins, to be used by 'getBestResult'
     *
     * @param origins accepted origins
     */
    default void setAcceptedOrigins(List<String> origins) {
        acceptedOrigins.clear();
        acceptedOrigins.addAll(origins);
    }
}
