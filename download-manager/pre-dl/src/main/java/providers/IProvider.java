package providers;

import data.ResultData;
import enums.Quality;
import exceptions.ResultNotFoundException;

import java.util.List;

public interface IProvider {

    String getName();

    List<ResultData> search(String tvShowName, int season, int episode, Quality quality);

    default ResultData getBestResult(List<ResultData> results) {
        return results.stream()
                .filter(d -> d.getSeeds() > 100)
                .findFirst()
                .orElseThrow(ResultNotFoundException::new);
    }

    void downloadFile(ResultData resultData);
}
