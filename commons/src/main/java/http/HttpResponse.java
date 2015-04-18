package http;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface HttpResponse {

    /**
     * @return true if successful (200 OK), false otherwise
     */
    boolean isSuccessful();

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

    /**
     * @return response body after un-zipping
     */
    String getUnzippedBody();

    /**
     * @return optional response body after un-zipping
     */
    Optional<String> tryGetUnzippedBody();

    /**
     * @return response body as stream
     */
    InputStream getBodyAsStream();
}
