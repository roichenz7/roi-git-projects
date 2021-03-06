package http;

import com.ning.http.client.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.zip.GZIPInputStream;

public class DefaultHttpResponse implements HttpResponse {

    private final Response response;

    public DefaultHttpResponse(Response response) {
        this.response = response;
    }

    @Override
    public boolean isSuccessful() {
        return response.getStatusCode() == 200 && response.getStatusText().equals("OK");
    }

    @Override
    public int getStatusCode() {
        return response.getStatusCode();
    }

    @Override
    public String getStatusText() {
        return response.getStatusText();
    }

    @Override
    public List<String> getHeaders(String name) {
        return response.getHeaders(name);
    }

    @Override
    public boolean isZipped() {
        return Optional.ofNullable(getHeaders("Content-Encoding"))
                .orElse(Collections.emptyList())
                .stream()
                .anyMatch(h -> h.equals("gzip"));
    }

    @Override
    public String getBody() {
        try {
            return response.getResponseBody();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getUnzippedBody() {
        StringBuilder responseBody = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new GZIPInputStream(getBodyAsStream())))) {
            String line;
            while ((line = in.readLine()) != null) {
                responseBody.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseBody.toString();
    }

    @Override
    public Optional<String> tryGetUnzippedBody() {
        try {
            return Optional.of(getUnzippedBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public InputStream getBodyAsStream() {
        try {
            return response.getResponseBodyAsStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InputStream getBodyAsZippedStream() {
        try {
            return new GZIPInputStream(getBodyAsStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "DefaultHttpResponse{" + getStatusCode() + " " + getStatusText() + "}";
    }
}