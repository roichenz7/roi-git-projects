package providers.torrent;

import data.SearchQuery;
import data.SearchResult;
import exceptions.SearchException;
import http.DefaultHttpRequestBuilder;
import http.HttpMethod;
import http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import providers.ProviderBase;
import providers.torrent.results.RarbgSearchResult;

import java.util.List;
import java.util.stream.Collectors;

public class RarbgProvider extends ProviderBase implements TorrentProvider {

    public RarbgProvider(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public List<SearchResult> search(SearchQuery searchQuery) {
        final String query = searchQuery.toString().replaceAll(" ", "+");

        HttpResponse response;
        try {
            response = new DefaultHttpRequestBuilder(HttpMethod.GET, getBaseUrl() + "/torrents.php")
                    .withUrlParam("search", query)
                    .withHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0")
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
        return document.select("tr [class=lista2]")
                .stream()
                .filter(e -> e.select("td").size() == 8)
                .map(e -> new RarbgSearchResult(e, getBaseUrl()))
                .collect(Collectors.toList());
    }
}
