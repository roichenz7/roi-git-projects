package providers.torrent;

import data.RequestData;
import data.ResultData;
import exceptions.SearchException;
import http.HttpMethod;
import http.HttpRequestBuilder;
import http.IHttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
    public List<ResultData> search(RequestData requestData) {
        final String query = requestData.toString().replaceAll(" ", "%20");

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
                .map(PirateBayResultData::new)
                .collect(Collectors.toList());
    }

    /**
     * PirateBay result data inner class
     */
    private static class PirateBayResultData extends ResultData {

        public PirateBayResultData(Element source) {
            super(source);
        }

        @Override
        protected void initialize(Element source) {
            // TODO
        }
    }
}