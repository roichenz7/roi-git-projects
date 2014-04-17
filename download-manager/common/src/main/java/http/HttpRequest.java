package http;

import com.ning.http.client.AsyncHttpClient;

public class HttpRequest implements IHttpRequest {

    private AsyncHttpClient.BoundRequestBuilder builder;

    public HttpRequest(AsyncHttpClient.BoundRequestBuilder builder) {
        this.builder = builder;
    }

    @Override
    public IHttpResponse execute() throws Exception {
        return new HttpResponse(builder.execute().get());
    }
}
