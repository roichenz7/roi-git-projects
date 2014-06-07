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
                .orElseThrow(ResultNotFoundException::new);
    }

    @Override
    default void download(SearchResult result) {
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.SRT);
    }
}
