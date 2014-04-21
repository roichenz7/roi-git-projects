package providers;

import data.ResultData;
import enums.Quality;
import exceptions.SearchException;
import http.HttpMethod;
import http.HttpRequestBuilder;
import http.IHttpResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        final String query = String.format("%s s%02de%02d %s", tvShowName, season, episode, quality);

        IHttpResponse response;
        try {
            response = new HttpRequestBuilder(HttpMethod.GET, getBaseUrl() + "/index.php")
                    .withUrlParam("page", "torrents")
                    .withUrlParam("search", query)
                    .withUrlParam("active", "0")
                    .execute();
        } catch (Exception e) {
            throw new SearchException(query, e);
        }

        if (response.getStatusCode() != 200 || !response.getStatusText().equals("OK")) {
            throw new SearchException(query, "Http response: " + response);
        }

        Pattern pattern = Pattern.compile("<tr>[^(</tr>)]*</tr>", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(response.getBody());
        List<String> values = new ArrayList<>();
        while (matcher.find()) {
            values.add(matcher.group(1));
        }

        return values.stream()
                .map(ResultData::new)
                .collect(Collectors.toList());
    }
}
