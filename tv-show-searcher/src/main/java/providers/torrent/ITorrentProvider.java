package providers.torrent;

import data.ResultData;
import exceptions.ResultNotFoundException;
import providers.IProvider;

import java.util.List;

public interface ITorrentProvider extends IProvider {

    /**
     * Returns best result from given results
     *
     * @param results results
     * @return best result
     */
    default ResultData getBestResult(List<ResultData> results) {
        return results.stream()
                .filter(d -> acceptedOrigins.contains(d.getOrigin()))
                .sorted((l, r) -> r.getSeeds() - l.getSeeds())
                .findFirst()
                .orElseThrow(ResultNotFoundException::new);
    }
}
