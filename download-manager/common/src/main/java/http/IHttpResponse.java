package http;

import java.util.List;

public interface IHttpResponse {

    /**
     * @return response status code
     */
    int getStatusCode();

    /**
     * @return response status text
     */
    String getStatusText();

    /**
     * @return response headers according to name
     */
    List<String> getHeaders(String name);

    /**
     * @return response body
     */
    String getBody();
}
