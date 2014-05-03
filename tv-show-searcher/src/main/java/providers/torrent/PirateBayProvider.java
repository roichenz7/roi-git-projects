package providers.torrent;

import data.SearchQuery;
import data.SearchResult;
import data.ShowData;
import exceptions.SearchException;
import http.HttpMethod;
import http.HttpRequestBuilder;
import http.IHttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PirateBayProvider implements ITorrentProvider {

    @Override
    public String getName() {
        return "PirateBay";
    }

    @Override
    public String getBaseUrl() {
        return "http://thepiratebay.se";
    }

    @Override
    public List<SearchResult> search(SearchQuery searchQuery) {
        final String query = searchQuery.toString().replaceAll(" ", "%20");

        IHttpResponse response;
        try {
            response = new HttpRequestBuilder(HttpMethod.GET, getBaseUrl() + "/search/" + query + "/")
                    .execute();
        } catch (Exception e) {
            throw new SearchException(query, e);
        }

        if (response.getStatusCode() != 200 || !response.getStatusText().equals("OK")) {
            throw new SearchException(query, "Http response: " + response);
        }

        Document document = Jsoup.parse(response.getBody());
        return document.select("tr")
                .stream()
                .filter(e -> e.select("td").size() == 4)
                .filter(e -> {
                    Matcher matcher = Pattern.compile("<a href=\"magnet:?")
                            .matcher(e.html());
                    return matcher.find() && !matcher.find();
                })
                .map(PirateBaySearchResult::new)
                .collect(Collectors.toList());
    }

    /**
     * PirateBay search result inner class
     */
    private static class PirateBaySearchResult extends SearchResult {

        public PirateBaySearchResult(Element source) {
            super(source);
        }

        @Override
        protected void initialize(Element source) {
            Elements elements = source.getElementsByTag("td");

            String temp = elements.get(1).getElementsByAttribute("href").get(0).attr("href");
            String [] array = temp.split("/");
            String id = array[2];
            String showName = array[3];

            ShowData showData = ShowData.fromFilename(showName);
            initialize(showData);

            seeds = Integer.parseInt(elements.get(2).text());
            peers = Integer.parseInt(elements.get(3).text());

            downloadLink = "http://piratebaytorrents.info/" + id + "/" + showName + "." + id + ".TPB.torrent";
        }
    }
}
