package http;

public interface IHttpRequest {

    /**
     * Executes this request
     *
     * @return http response
     */
    IHttpResponse execute();
}
