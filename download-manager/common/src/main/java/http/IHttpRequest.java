package http;

public interface IHttpRequest {

    /**
     * Executes this request
     *
     * @return http response
     * @throws Exception
     */
    IHttpResponse execute() throws Exception;
}
