package http;

public interface HttpRequest {

    /**
     * Executes this request
     *
     * @return http response
     */
    HttpResponse execute();
}
