package providers;

import data.RequestData;
import data.ResultData;
import data.ShowData;
import enums.Quality;
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
    public List<ResultData> search(RequestData requestData) {
        IHttpResponse response;
        try {
            response = new HttpRequestBuilder(HttpMethod.GET, getBaseUrl() + "/index.php")
                    .withUrlParam("page", "torrents")
                    .withUrlParam("search", requestData.toString())
                    .withUrlParam("active", "0")
                    .execute();
        } catch (Exception e) {
            throw new SearchException(requestData.toString(), e);
        }

        if (response.getStatusCode() != 200 || !response.getStatusText().equals("OK")) {
            throw new SearchException(requestData.toString(), "Http response: " + response);
        }

        Document document = Jsoup.parse(response.getBody());
        return document.select("tr")
                .stream()
                .filter(e -> {
                    Matcher matcher = Pattern.compile("<a href=\"magnet:?")
                            .matcher(e.html());
                    return matcher.find() && !matcher.find();
                })
                .map(PhdResultData::new)
                .collect(Collectors.toList());
    }

    /**
     * PHD result data inner class
     */
    private static class PhdResultData extends ResultData {

        public PhdResultData(Element source) {
            super(source);
        }

        @Override
        protected void initialize(Element source) {
            Elements elements = source.getElementsByTag("td");

            ShowData showData = ShowData.fromFilename(elements.get(1).text(), " ");
            tvShowName = showData.getTitle();
            season = showData.getSeasonNumber();
            episode = showData.getEpisodeNumber();

            quality = Quality.fromString(showData.getQuality());
            origin = showData.getOrigin();
            isProper = showData.isProper();

            seeds = Integer.parseInt(elements.get(4).text());
            peers = Integer.parseInt(elements.get(5).text());

            String temp = elements.get(2).getElementsByAttribute("href").get(2).attr("href");
            if (temp.contains("istoretor")) {
                String[] array = temp.replaceAll("http://", "").split("/");
                String hash = array[2].toUpperCase();
                downloadLink = "http://istoretor.com/fdown.php?hash=" + hash;
            }
        }
    }
}
