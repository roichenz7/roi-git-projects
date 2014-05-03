package providers.torrent;

import data.SearchResult;
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
    default SearchResult getBestResult(List<SearchResult> results) {
        return results.stream()
                .filter(d -> acceptedOrigins.contains(d.getOrigin()))
                .sorted((l, r) -> r.getSeeds() - l.getSeeds())
                .findFirst()
                .orElseThrow(ResultNotFoundException::new);
    }
}
