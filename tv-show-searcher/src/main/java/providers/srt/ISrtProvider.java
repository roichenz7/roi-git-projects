package providers.srt;

import data.SearchQuery;
import data.SearchResult;
import exceptions.ResultNotFoundException;
import providers.IProvider;

import java.util.List;

public interface ISrtProvider extends IProvider {

    @Override
    default SearchResult getBestResult(List<SearchResult> results, SearchQuery query) {
        return results.stream()
                .filter(x -> x.getQuality().equals(query.getQuality()))
                .filter(x -> acceptedOrigins.contains(x.getOrigin()))
                .findFirst()
                .orElseThrow(ResultNotFoundException::new);
    }
}
