package providers;

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
     * Performs search according to given parameters
     *
     * @param tvShowName tv show name
     * @param season season number
     * @param episode episode number
     * @param quality file quality
     * @return list of results
     */
    List<ResultData> search(String tvShowName, int season, int episode, Quality quality);

    /**
     * Returns best result from given results
     *
     * @param results results
     * @return best result
     */
    default ResultData getBestResult(List<ResultData> results) {
        return results.stream()
                .filter(d -> acceptedOrigins().contains(d.getOrigin()))
                .sorted((l, r) -> l.getSeeds() - r.getSeeds())
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
