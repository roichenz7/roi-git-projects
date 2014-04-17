package service;

import com.ning.http.client.cookie.Cookie;
import exceptions.LoginException;
import http.HttpMethod;
import http.HttpRequestBuilder;
import http.IHttpResponse;

import java.util.List;

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
            response = new HttpRequestBuilder(HttpMethod.POST, baseUrl + "/login.php")
                    .withUrlParam("username", username)
                    .withUrlParam("pass", password)
                    .withUrlParam("action", "Login")
                    .withUrlParam("u", "")
                    .execute();
        } catch (Exception e) {
            throw new LoginException(username, e);
        }

        if (response.getStatusCode() != 200 || !response.getStatusText().equals("OK")) {
            throw new LoginException(username, "Http response: " + response);
        }

        List<String> headers = response.getHeaders("Set-Cookie");
        // TODO: save cookies
    }
}
