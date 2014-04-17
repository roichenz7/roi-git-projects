package service;

import exceptions.LoginException;
import exceptions.UpdateException;
import http.HttpMethod;
import http.HttpRequestBuilder;
import http.IHttpResponse;

public class MyEpisodesServiceImpl implements MyEpisodesService {

    private final String baseUrl = "http://www.myepisodes.com";
    private String cookies = "";

    public MyEpisodesServiceImpl(final String username, final String password) {
        login(username, password);
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
                    .withHeader("Cookie", cookies)
                    .execute();
        } catch (Exception e) {
            throw new UpdateException(showId, season, episode, e);
        }

        if (response.getStatusCode() != 200 || !response.getStatusText().equals("OK")) {
            throw new UpdateException(showId, season, episode, "Http response: " + response);
        }
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
                    .withHeader("Cookie", cookies)
                    .execute();
        } catch (Exception e) {
            throw new UpdateException(showId, season, episode, e);
        }

        if (response.getStatusCode() != 200 || !response.getStatusText().equals("OK")) {
            throw new UpdateException(showId, season, episode, "Http response: " + response);
        }
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

        response.getHeaders("Set-Cookie")
                .forEach(cookie -> cookies += (cookie + "; "));
    }
}
