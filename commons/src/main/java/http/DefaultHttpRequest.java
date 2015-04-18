package http;

import com.ning.http.client.AsyncHttpClient;

public class DefaultHttpRequest implements IHttpRequest {

    private AsyncHttpClient.BoundRequestBuilder builder;

    public DefaultHttpRequest(AsyncHttpClient.BoundRequestBuilder builder) {
        this.builder = builder;
    }

    @Override
    public IHttpResponse execute() {
        try {
            return new DefaultHttpResponse(builder.execute().get());
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute http request", e);
        }
    }
}
