package service;

import com.ning.http.client.cookie.Cookie;
import data.EpisodeData;
import data.ITvShowParser;
import data.TvShowData;
import data.TvShowParser;
import exceptions.GetMyShowsException;
import exceptions.GetStatusException;
import exceptions.LoginException;
import exceptions.UpdateException;
import http.DefaultHttpRequestBuilder;
import http.HttpMethod;
import http.IHttpResponse;
import http.cookies.CookieAdapter;
import http.cookies.CookieListAdapter;
import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyEpisodesServiceImpl implements MyEpisodesService {

    private final String baseUrl = "http://www.myepisodes.com";
    private final ITvShowParser tvShowParser = new TvShowParser();

    private String username;
    private String passwordMD5;
    private List<Cookie> cookies;

    public MyEpisodesServiceImpl(final String username, final String password) {
        login(username, password);
    }

    public MyEpisodesServiceImpl(final String username, final String password, final String configFilename) {
        this(username, password);
        tvShowParser.deserialize(configFilename);
    }

    @Override
    public Map<Integer, String> getMyShows() {
        IHttpResponse response;
        try {
            response = new DefaultHttpRequestBuilder(HttpMethod.GET, baseUrl + "/shows.php")
                    .withUrlParam("type", "manage")
                    .withHeader("Cookie", cookies.toString())
                    .execute();
        } catch (Exception e) {
            throw new GetMyShowsException(e);
        }

        if (response.getStatusCode() != 200 || !response.getStatusText().equals("OK")) {
            throw new GetMyShowsException("Http response: " + response);
        }

        Document document = Jsoup.parse(response.getBody());
        return document.select("table tbody tr td")
                .stream()
                .filter(e -> e.html().contains("Your Favorite Show List"))
                .collect(Collectors.<Element>toList())
                .get(0)
                .select("option")
                .stream()
                .collect(Collectors.toMap(o -> Integer.parseInt(o.val()), Element::text));
    }

    @Override
    public List<TvShowData> getStatus() {
        Map<Integer, TvShowData> tvShows = getMyShows()
                .entrySet()
                .stream()
                .map(TvShowData::new)
                .collect(Collectors.toMap(TvShowData::getId,
                        Function.<TvShowData>identity()));

        getUnAcquiredEpisodes().forEach(e ->
                tvShows.get(e.getTvShowId())
                        .addUnAcquiredEpisode(e));

        getUnSeenEpisodes().forEach(e ->
                tvShows.get(e.getTvShowId())
                        .addUnSeenEpisode(e));

        return tvShows.values()
                .stream()
                .sorted((l, r) -> l.getName().compareTo(r.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<EpisodeData> getUnAcquiredEpisodes() {
        IHttpResponse response;
        try {
            response = new DefaultHttpRequestBuilder(HttpMethod.GET, baseUrl + "/rss.php")
                    .withUrlParam("feed", "unacquired")
                    .withUrlParam("uid", username)
                    .withUrlParam("pwdmd5", passwordMD5)
                    .execute();
        } catch (Exception e) {
            throw new GetStatusException(e);
        }

        if (response.getStatusCode() != 200 || !response.getStatusText().equals("OK")) {
            throw new GetStatusException("Http response: " + response);
        }

        Document document = Jsoup.parse(response.getBody());
        return parseRssResponse(document);
    }

    @Override
    public Collection<EpisodeData> getUnSeenEpisodes() {
        IHttpResponse response;
        try {
            response = new DefaultHttpRequestBuilder(HttpMethod.GET, baseUrl + "/rss.php")
                    .withUrlParam("feed", "unwatched")
                    .withUrlParam("uid", username)
                    .withUrlParam("pwdmd5", passwordMD5)
                    .execute();
        } catch (Exception e) {
            throw new GetStatusException(e);
        }

        if (response.getStatusCode() != 200 || !response.getStatusText().equals("OK")) {
            throw new GetStatusException("Http response: " + response);
        }

        Document document = Jsoup.parse(response.getBody());
        return parseRssResponse(document);
    }

    @Override
    public void markAsAcquired(int showId, int season, int episode) {
        IHttpResponse response;
        try {
            response = new DefaultHttpRequestBuilder(HttpMethod.GET, baseUrl + "/myshows.php")
                    .withUrlParam("action", "Update")
                    .withUrlParam("showid", String.valueOf(showId))
                    .withUrlParam("season", String.valueOf(season))
                    .withUrlParam("episode", String.valueOf(episode))
                    .withUrlParam("seen", "0")
                    .withHeader("Cookie", cookies.toString())
                    .execute();
        } catch (Exception e) {
            throw new UpdateException(showId, season, episode, e);
        }

        if (response.getStatusCode() != 200 || !response.getStatusText().equals("OK")) {
            throw new UpdateException(showId, season, episode, "Http response: " + response);
        }
    }

    @Override
    public void markAsAcquired(String showName, int season, int episode) {
        TvShowData tvShowData = tvShowParser.getShowByName(showName);
        if (tvShowData == null)
            throw new UpdateException(showName, season, episode, "Show doesn't exist in configuration");

        markAsAcquired(tvShowData.getId(), season, episode);
    }

    @Override
    public void markAsWatched(int showId, int season, int episode) {
        IHttpResponse response;
        try {
            response = new DefaultHttpRequestBuilder(HttpMethod.GET, baseUrl + "/myshows.php")
                    .withUrlParam("action", "Update")
                    .withUrlParam("showid", String.valueOf(showId))
                    .withUrlParam("season", String.valueOf(season))
                    .withUrlParam("episode", String.valueOf(episode))
                    .withUrlParam("seen", "1")
                    .withHeader("Cookie", cookies.toString())
                    .execute();
        } catch (Exception e) {
            throw new UpdateException(showId, season, episode, e);
        }

        if (response.getStatusCode() != 200 || !response.getStatusText().equals("OK")) {
            throw new UpdateException(showId, season, episode, "Http response: " + response);
        }
    }

    @Override
    public void markAsWatched(String showName, int season, int episode) {
        TvShowData tvShowData = tvShowParser.getShowByName(showName);
        if (tvShowData == null)
            throw new UpdateException(showName, season, episode, "Show doesn't exist in configuration");

        markAsWatched(tvShowData.getId(), season, episode);
    }

    /**
     * Performs login to myepisodes
     *
     * @param username user name
     * @param password password
     */
    private void login(final String username, final String password) {
        IHttpResponse response;
        try {
            final String body = String.format("username=%s&password=%s&action=Login&u=", username, password);
            response = new DefaultHttpRequestBuilder(HttpMethod.POST, baseUrl + "/login.php")
                    .withHeader("Content-Type", "application/x-www-form-urlencoded")
                    .withBody(body)
                    .execute();
        } catch (Exception e) {
            throw new LoginException(username, e);
        }

        this.username = username;
        passwordMD5 = DigestUtils.md5Hex(password);
        cookies = response.getHeaders("Set-Cookie")
                .stream()
                .map(CookieAdapter::fromString)
                .collect(CookieListAdapter.toCookieList());

        if (cookies.size() != 4)
            throw new LoginException(username, "invalid user or password");
    }

    /**
     * Parses rss feed response
     *
     * @param document response document
     * @return collection of episodes
     */
    private List<EpisodeData> parseRssResponse(Document document) {
        return document.select("item")
                .stream()
                .filter(e -> !e.html().contains("<title>No Episodes</title>"))
                .map(EpisodeData::new)
                .collect(Collectors.toList());
    }
}
