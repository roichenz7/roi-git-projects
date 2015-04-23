package providers.torrent;

import data.SearchQuery;
import data.SearchResult;
import exceptions.SearchException;
import http.DefaultHttpRequestBuilder;
import http.HttpMethod;
import http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import providers.torrent.results.RarbgSearchResult;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RarbgProvider implements TorrentProvider {

    public static final String BASE_URL = "http://rarbg.to";

    @Override
    public String getName() {
        return "RARBG";
    }

    @Override
    public String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    public List<SearchResult> search(SearchQuery searchQuery) {
        final String query = searchQuery.toString().replaceAll(" ", "+");

        HttpResponse response;
        try {
            response = new DefaultHttpRequestBuilder(HttpMethod.GET, getBaseUrl() + "/torrents.php")
                    .withUrlParam("search", query)
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

        final AtomicInteger index = new AtomicInteger();
        Document document = Jsoup.parse(body);
        return document.select("tr [class=lista2]")
                .stream()
                .filter(e -> e.select("td").size() == 8)
                .filter(e -> index.getAndIncrement() % 2 == 1)
                .map(RarbgSearchResult::new)
                .collect(Collectors.toList());
    }
}
