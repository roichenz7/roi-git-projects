package http.cookies;

import com.ning.http.client.cookie.Cookie;

public class CookieAdapter extends Cookie {

    private String name;
    private String value;
    private String expirationTime;
    private final String path;

    public CookieAdapter(String name, String value, String expirationTime) {
        this(name, value, expirationTime, "/");
    }

    public CookieAdapter(String name, String value, String expirationTime, String path) {
        super(name, value, "", "", null, Long.MAX_VALUE, Integer.MAX_VALUE, false, false);

        this.name = name;
        this.value = value;
        this.expirationTime = expirationTime;
        this.path = path;
    }

    @Override
    public String toString() {
        return name + "=" + value +
                "; expires=" + expirationTime +
                "; path=" + path;
    }

    public static CookieAdapter fromString(String source) {
        final String[] array = source.split("; ");
        final String[] nameAndValue = array[0].split("=");
        final String[] expiration = array[1].split("=");
        return new CookieAdapter(nameAndValue[0], nameAndValue[1], expiration[1]);
    }
}
