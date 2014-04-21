package providers;

import data.ResultData;
import enums.Quality;
import exceptions.SearchException;
import http.HttpMethod;
import http.HttpRequestBuilder;
import http.IHttpResponse;

import java.util.ArrayList;
import java.util.List;

public class PhdProvider implements IProvider {

    @Override
    public final String getName() {
        return "PHD";
    }

    @Override
    public final String getBaseUrl() {
        return "http://publichd.se";
    }

    @Override
    public List<ResultData> search(String tvShowName, int season, int episode, Quality quality) {
        final String query = String.format("%s+s%02de%02d+%s", tvShowName, season, episode, quality);

        IHttpResponse response;
        try {
            response = new HttpRequestBuilder(HttpMethod.GET, getBaseUrl() + "/index.php")
                    .withUrlParam("page", "torrents")
                    .withUrlParam("search", query)
                    .withUrlParam("active", "0")
                    .withHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:28.0) Gecko/20100101 Firefox/28.0")
                    .withAccept("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .withHeader("Accept-Language", "en-US,en;q=0.5")
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
