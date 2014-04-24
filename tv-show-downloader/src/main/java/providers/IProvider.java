package providers;

import data.RequestData;
import data.ResultData;
import enums.Quality;
import exceptions.ResultNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface IProvider {

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
    default ResultData getBestResult(List<ResultData> results) {
        return results.stream()
                .filter(d -> acceptedOrigins().contains(d.getOrigin()))
                .sorted((l, r) -> r.getSeeds() - l.getSeeds())
                .findFirst()
                .orElseThrow(ResultNotFoundException::new);
    }

    /**
     * @return accepted origins
     */
    default Collection<String> acceptedOrigins() {
        return Collections.unmodifiableList(Arrays.asList("DIMENSION",
                "2HD",
                "KILLERS",
                "REMARKABLE",
                "PublicHD"));
    }
}
