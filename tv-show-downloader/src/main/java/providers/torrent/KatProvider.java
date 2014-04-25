package providers.torrent;

import data.RequestData;
import data.ResultData;
import exceptions.SearchException;
import http.HttpMethod;
import http.HttpRequestBuilder;
import http.IHttpResponse;

import java.util.ArrayList;
import java.util.List;

public class KatProvider implements ITorrentProvider {

    @Override
    public final String getName() {
        return "KAT";
    }

    @Override
    public final String getBaseUrl() {
        return "http://kickass.to";
    }

    @Override
    public List<ResultData> search(RequestData requestData) {
        final String query = requestData.toString().replaceAll(" ", "%20");

        IHttpResponse response;
        try {
            response = new HttpRequestBuilder(HttpMethod.GET, getBaseUrl() + "/usearch/" + query + "/")
                    .withHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:28.0) Gecko/20100101 Firefox/28.0")
                    .withAccept("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .withHeader("Accept-Language", "en-US,en;q=0.5")
                    .withHeader("Accept-Encoding", "gzip, deflate")
                    .withHeader("DNT", "1")
                    .withHeader("Cookie", "country_code=IL; state=1398091871535; _ga=GA1.2.543244531.1398081658; lang_detected=en; lang_code=en")
                    .withHeader("Connection", "keep-alive")
                    .withHeader("Cache-Control", "max-age=0")
                    .execute();
        } catch (Exception e) {
            throw new SearchException(query, e);
        }

        if (response.getStatusCode() != 200 || !response.getStatusText().equals("OK")) {
            throw new SearchException(query, "Http response: " + response);
        }

        String body = response.getBody();
        // TODO

        return new ArrayList<>();
    }
}
