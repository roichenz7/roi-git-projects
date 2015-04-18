package http;

import com.ning.http.client.cookie.Cookie;

public interface HttpRequestBuilder {

    /**
     * Adds a url parameter with given name and value
     *
     * @param name  parameter name
     * @param value parameter value
     * @return self reference
     */
    HttpRequestBuilder withUrlParam(String name, String value);

    /**
     * Adds a header (name,value) to request headers
     *
     * @param name  header name
     * @param value header value
     * @return self reference
     */
    HttpRequestBuilder withHeader(String name, String value);

    /**
     * Adds an accept header ('Accept',value) to request headers
     *
     * @param value header value
     * @return self reference
     */
    HttpRequestBuilder withAccept(String value);

    /**
     * Adds given cookies to request
     *
     * @return self reference
     */
    HttpRequestBuilder withCookies(Cookie... cookies);

    /**
     * Adds given cookies to request
     *
     * @return self reference
     */
    HttpRequestBuilder withCookies(Iterable<Cookie> cookies);

    /**
     * Sets request body
     *
     * @param body request body
     * @return self reference
     */
    HttpRequestBuilder withBody(String body);

    /**
     * Builds this request
     * IMPORTANT: you must invoke the close method of this builder after executing the http request
     *
     * @return built request
     */
    HttpRequest build();

    /**
     * Builds this request, executes it, and closes the connection used by this builder
     *
     * @return http response
     */
    HttpResponse execute();

    /**
     * Closes the connection used by this builder
     */
    void close();
}
