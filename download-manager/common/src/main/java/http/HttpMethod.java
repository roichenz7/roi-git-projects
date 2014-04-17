package http;

/**
 * Http method enumeration
 */
public enum HttpMethod {

    GET("GET"),
    POST("POST");

    private String name;

    HttpMethod(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
