package providers.srt;

import data.SearchQuery;
import data.SearchResult;
import exceptions.ResultNotFoundException;
import file.FileDownloader;
import file.FileType;
import providers.Provider;

import java.util.List;

public interface SrtProvider extends Provider {

    @Override
    default SearchResult getBestResult(List<SearchResult> results, SearchQuery query) {
        return results.stream()
                .filter(x -> x.matches(query))
                .filter(x -> acceptedOrigins.contains(x.getOrigin()))
                .findFirst()
                .orElseThrow(() -> new ResultNotFoundException("search query: " + query));
    }

    @Override
    default void download(SearchResult result, String downloadPath) {
        String filename = result.toString() + ".[" + getName() + "]";
        FileDownloader.downloadFile(result.getDownloadLink(), downloadPath, filename, FileType.SRT);
    }
}
