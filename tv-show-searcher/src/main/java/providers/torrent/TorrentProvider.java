package providers.torrent;

import data.SearchQuery;
import data.SearchResult;
import exceptions.ResultNotFoundException;
import file.FileDownloader;
import file.FileType;
import file.FileUtils;
import http.DefaultHttpRequestBuilder;
import http.HttpMethod;
import http.HttpResponse;
import providers.Provider;

import java.io.InputStream;
import java.util.List;
import java.util.function.Function;

public interface TorrentProvider extends Provider {

    @Override
    default SearchResult getBestResult(List<SearchResult> results, SearchQuery query) {
        return results.stream()
                .filter(x -> x.matches(query))
                .filter(x -> acceptedOrigins.contains(x.getOrigin()))
                .sorted((l, r) -> r.getScore() - l.getScore())
                .findFirst()
                .orElseThrow(() -> new ResultNotFoundException("search query: " + query));
    }

    @Override
    default void download(SearchResult result, String downloadPath) {
        String filename = result.toString() + ".[" + getName() + "]";
        FileDownloader.downloadFile(result.getDownloadLink(), downloadFunction(), downloadPath, filename, FileType.TORRENT);
    }

    default Function<String, InputStream> downloadFunction() {
        return downloadLink -> {
            HttpResponse response = new DefaultHttpRequestBuilder(HttpMethod.GET, downloadLink).execute();
            return FileUtils.gzipIfNeeded(response.getBodyAsStream());
        };
    }
}
