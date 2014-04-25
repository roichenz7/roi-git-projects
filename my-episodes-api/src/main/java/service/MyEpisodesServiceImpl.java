package service;

import com.ning.http.client.cookie.Cookie;
import data.EpisodeData;
import data.ITvShowParser;
import data.TvShowData;
import data.TvShowParser;
import exceptions.GetStatusException;
import exceptions.LoginException;
import exceptions.UpdateException;
import http.HttpMethod;
import http.HttpRequestBuilder;
import http.IHttpResponse;
import http.cookies.CookieAdapter;
import http.cookies.CookieListAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyEpisodesServiceImpl implements MyEpisodesService {

    private final String baseUrl = "http://www.myepisodes.com";
    private final ITvShowParser tvShowParser = new TvShowParser();
    private List<Cookie> cookies;

    public MyEpisodesServiceImpl(final String username, final String password) {
        login(username, password);
    }

    public MyEpisodesServiceImpl(final String username, final String password, final String configFilename) {
        this(username, password);
        tvShowParser.deserialize(configFilename);
    }

    @Override
    public List<TvShowData> getStatus() {
        Map<Integer, TvShowData> tvShows = tvShowParser.getAll()
                .stream()
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
                .collect(Collectors.toList());
    }

    @Override
    public Collection<EpisodeData> getUnAcquiredEpisodes() {
        return new ArrayList<>();
    }

    @Override
    public Collection<EpisodeData> getUnSeenEpisodes() {
        return new ArrayList<>();
    }

    @Override
    public void markAsAcquired(int showId, int season, int episode) {
        IHttpResponse response;
        try {
            response = new HttpRequestBuilder(HttpMethod.GET, baseUrl + "/myshows.php")
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
            response = new HttpRequestBuilder(HttpMethod.GET, baseUrl + "/myshows.php")
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

    private void login(final String username, final String password) {
        IHttpResponse response;
        try {
            final String body = String.format("username=%s&password=%s&action=Login&u=", username, password);
            response = new HttpRequestBuilder(HttpMethod.POST, baseUrl + "/login.php")
                    .withHeader("Content-Type", "application/x-www-form-urlencoded")
                    .withBody(body)
                    .execute();
        } catch (Exception e) {
            throw new LoginException(username, e);
        }

        cookies = response.getHeaders("Set-Cookie")
                .stream()
                .map(CookieAdapter::fromString)
                .collect(CookieListAdapter.toCookieList());
    }
}
