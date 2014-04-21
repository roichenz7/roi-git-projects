package providers;

import data.ResultData;
import enums.Quality;
import exceptions.ResultNotFoundException;

import java.util.List;

public interface IProvider {

    /**
     * @return provider name
     */
    String getName();

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
                .filter(d -> d.getSeeds() > 100)
                .findFirst()
                .orElseThrow(ResultNotFoundException::new);
    }

    /**
     * Downloads file according to given result
     *
     * @param resultData result
     */
    void downloadFile(ResultData resultData);
}
