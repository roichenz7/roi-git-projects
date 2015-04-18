package http;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.cookie.Cookie;

import java.util.Arrays;

public class HttpRequestBuilder implements IHttpRequestBuilder {

    private AsyncHttpClient client;
    private AsyncHttpClient.BoundRequestBuilder builder;

    public HttpRequestBuilder(HttpMethod httpMethod, String url) {
        AsyncHttpClientConfig config = new AsyncHttpClientConfig
                .Builder()
                .setConnectionTimeoutInMs(10000)
                .build();

        client = new AsyncHttpClient(config);
        builder = new Factory().create(httpMethod, url);
    }

    @Override
    public IHttpRequestBuilder withUrlParam(String name, String value) {
        builder.addQueryParameter(name, value);
        return this;
    }

    @Override
    public IHttpRequestBuilder withHeader(String name, String value) {
        builder.addHeader(name, value);
        return this;
    }

    @Override
    public IHttpRequestBuilder withAccept(String value) {
        return withHeader("Accept", value);
    }

    @Override
    public IHttpRequestBuilder withCookies(Cookie... cookies) {
        return withCookies(Arrays.asList(cookies));
    }

    @Override
    public IHttpRequestBuilder withCookies(Iterable<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            builder.addCookie(cookie);
        }
        return this;
    }

    @Override
    public IHttpRequestBuilder withBody(String body) {
        builder.setBody(body);
        builder.setBodyEncoding("UTF-8");
        return this;
    }

    @Override
    public IHttpRequest build() {
        return new HttpRequest(builder);
    }

    @Override
    public IHttpResponse execute() {
        IHttpResponse response = build().execute();
        close();
        return response;
    }

    @Override
    public void close() {
        if (!client.isClosed())
            client.closeAsynchronously();
    }

    /**
     * Factory inner class
     */
    private class Factory {
        /**
         * Creates bound request builder according to given url and http method
         *
         * @param httpMethod http method
         * @param url        url
         * @return created bound request builder
         */
        private AsyncHttpClient.BoundRequestBuilder create(HttpMethod httpMethod, String url) {
            switch (httpMethod) {
                case GET:
                    return client.prepareGet(url);
                case POST:
                    return client.preparePost(url);
                default:
                    throw new IllegalArgumentException("Illegal http method provided: " + httpMethod);
            }
        }
    }
}
