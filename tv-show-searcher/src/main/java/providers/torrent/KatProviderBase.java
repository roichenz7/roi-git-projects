package providers.torrent;

import data.SearchQuery;
import data.SearchResult;
import exceptions.SearchException;
import file.FileDownloader;
import file.FileType;
import http.DefaultHttpRequestBuilder;
import http.HttpMethod;
import http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import providers.torrent.results.KatSearchResult;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public abstract class KatProviderBase implements TorrentProvider {

    @Override
    public abstract String getName();

    @Override
    public abstract String getBaseUrl();

    @Override
    public List<SearchResult> search(SearchQuery searchQuery) {
        final String query = searchQuery.toString().replaceAll(" ", "%20");

        HttpResponse response;
        try {
            response = new DefaultHttpRequestBuilder(HttpMethod.GET, getBaseUrl() + "/usearch/" + query + "/")
                    .withHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:28.0) Gecko/20100101 Firefox/28.0")
                    .withAccept("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .withHeader("Accept-Language", "en-US,en;q=0.5")
                    .withHeader("Accept-Encoding", "gzip, deflate")
                    .execute();
        } catch (Exception e) {
            throw new SearchException(query, e);
        }

        if (!response.isSuccessful()) {
            throw new SearchException(query, "Http response: " + response);
        }

        String body = response.tryGetUnzippedBody()
                .orElseGet(response::getBody);

        Document document = Jsoup.parse(body);
        return document.select("tr")
                .stream()
                .filter(e -> e.select("td").size() == 6)
                .filter(e -> {
                    Matcher matcher = Pattern.compile("href=\"magnet:?")
                            .matcher(e.html());
                    return matcher.find() && !matcher.find();
                })
                .map(KatSearchResult::new)
                .collect(Collectors.toList());
    }

    @Override
    public void download(SearchResult result, String downloadPath) {
        String filename = result.toString() + ".[" + getName() + "]";
        FileDownloader.downloadFile(result.getDownloadLink(), downloadPath, filename, FileType.TORRENT, x -> {
            try {
                return new GZIPInputStream(x);
            } catch (IOException e) {
                return x;
            }
        });
    }
}
