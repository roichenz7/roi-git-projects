package providers;

import data.ResultData;
import enums.Quality;
import exceptions.SearchException;import http.HttpMethod;
import http.HttpRequestBuilder;
import http.IHttpResponse;

import java.util.ArrayList;
import java.util.List;

public class KatProvider implements IProvider {

    @Override
    public final String getName() {
        return "KAT";
    }

    @Override
    public final String getBaseUrl() {
        return "http://kickass.to";
    }

    @Override
    public List<ResultData> search(String tvShowName, int season, int episode, Quality quality) {
        final String query = tvShowName + " S" + season + "E" + episode + " " + quality;

        IHttpResponse response;
        try {
            response = new HttpRequestBuilder(HttpMethod.GET, getBaseUrl() + "/usearch/" + query)
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

    @Override
    public void downloadFile(ResultData resultData) {
    }
}
