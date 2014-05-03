package providers;

import data.SearchQuery;
import data.SearchResult;
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
     * Performs search according to searchQuery
     *
     * @param searchQuery search query
     * @return list of results
     */
    List<SearchResult> search(SearchQuery searchQuery);

    /**
     * Performs search according to given parameters
     *
     * @param tvShowName tv show name
     * @param season season number
     * @param episode episode number
     * @param quality file quality
     * @return list of results
     */
    default List<SearchResult> search(String tvShowName, int season, int episode, Quality quality) {
        return search(new SearchQuery(tvShowName, season, episode, quality));
    }

    /**
     * Returns best result from given results
     *
     * @param results results
     * @return best result
     */
    SearchResult getBestResult(List<SearchResult> results);

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
