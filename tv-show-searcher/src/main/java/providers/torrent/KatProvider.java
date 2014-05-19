package providers.torrent;

import data.SearchQuery;
import data.SearchResult;
import exceptions.SearchException;
import http.HttpMethod;
import http.HttpRequestBuilder;
import http.IHttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import providers.torrent.results.KatSearchResult;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KatProvider implements TorrentProvider {

    @Override
    public String getName() {
        return "KAT";
    }

    @Override
    public String getBaseUrl() {
        return "http://kickass.to";
    }

    @Override
    public List<SearchResult> search(SearchQuery searchQuery) {
        final String query = searchQuery.toString().replaceAll(" ", "%20");

        IHttpResponse response;
        try {
            response = new HttpRequestBuilder(HttpMethod.GET, getBaseUrl() + "/usearch/" + query + "/")
                    .withHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:28.0) Gecko/20100101 Firefox/28.0")
                    .withAccept("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .withHeader("Accept-Language", "en-US,en;q=0.5")
                    .withHeader("Accept-Encoding", "gzip, deflate")
                    .execute();
        } catch (Exception e) {
            throw new SearchException(query, e);
        }

        if (response.getStatusCode() != 200 || !response.getStatusText().equals("OK")) {
            throw new SearchException(query, "Http response: " + response);
        }

        Document document = Jsoup.parse(response.getUnzippedBody());
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
}
