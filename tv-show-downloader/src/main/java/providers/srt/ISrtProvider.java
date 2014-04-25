package providers.srt;

import data.ResultData;
import exceptions.ResultNotFoundException;
import providers.IProvider;

import java.util.List;

public interface ISrtProvider extends IProvider {

    /**
     * Returns best result from given results
     *
     * @param results results
     * @return best result
     */
    default ResultData getBestResult(List<ResultData> results) {
        return results.stream()
                .filter(d -> acceptedOrigins().contains(d.getOrigin()))
                .findFirst()
                .orElseThrow(ResultNotFoundException::new);
    }
}
