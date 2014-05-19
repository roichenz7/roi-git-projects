package providers.torrent;

import data.SearchQuery;
import data.SearchResult;
import exceptions.SearchException;
import http.HttpMethod;
import http.HttpRequestBuilder;
import http.IHttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import providers.torrent.results.PhdSearchResult;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PhdProvider implements TorrentProvider {

    @Override
    public String getName() {
        return "PHD";
    }

    @Override
    public String getBaseUrl() {
        return "http://phd.re";
    }

    @Override
    public List<SearchResult> search(SearchQuery searchQuery) {
        IHttpResponse response;
        try {
            response = new HttpRequestBuilder(HttpMethod.GET, getBaseUrl() + "/index.php")
                    .withUrlParam("page", "torrents")
                    .withUrlParam("search", searchQuery.toString())
                    .withUrlParam("active", "0")
                    .execute();
        } catch (Exception e) {
            throw new SearchException(searchQuery.toString(), e);
        }

        if (response.getStatusCode() != 200 || !response.getStatusText().equals("OK")) {
            throw new SearchException(searchQuery.toString(), "Http response: " + response);
        }

        Document document = Jsoup.parse(response.getBody());
        return document.select("tr")
                .stream()
                .filter(e -> e.select("td").size() == 8)
                .filter(e -> {
                    Matcher matcher = Pattern.compile("<a href=\"magnet:?")
                            .matcher(e.html());
                    return matcher.find() && !matcher.find();
                })
                .map(PhdSearchResult::new)
                .collect(Collectors.toList());
    }
}
