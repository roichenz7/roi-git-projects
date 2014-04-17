package service;

import com.ning.http.client.cookie.Cookie;
import exceptions.LoginException;
import http.CookieFactory;
import http.HttpMethod;
import http.HttpRequestBuilder;
import http.IHttpResponse;

import java.util.List;
import java.util.stream.Collectors;

public class MyEpisodesServiceImpl implements MyEpisodesService {

    private final String baseUrl = "http://www.myepisodes.com";
    private List<Cookie> cookies;

    public MyEpisodesServiceImpl(final String username, final String password) {
        login(username, password);
    }

    @Override
    public void markAsAcquired(int showId, int season, int episode) {

    }

    @Override
    public void markAsWatched(int showId, int season, int episode) {

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
                .map(CookieFactory::create)
                .collect(Collectors.toList());
    }
}
