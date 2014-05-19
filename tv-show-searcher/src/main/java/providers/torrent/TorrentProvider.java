package providers.torrent;

import data.SearchQuery;
import data.SearchResult;
import exceptions.ResultNotFoundException;
import providers.Provider;

import java.util.List;

public interface TorrentProvider extends Provider {

    @Override
    default SearchResult getBestResult(List<SearchResult> results, SearchQuery query) {
        return results.stream()
                .filter(x -> x.matches(query))
                .filter(x -> acceptedOrigins.contains(x.getOrigin()))
                .sorted((l, r) -> r.getSeeds() - l.getSeeds())
                .findFirst()
                .orElseThrow(ResultNotFoundException::new);
    }
}
