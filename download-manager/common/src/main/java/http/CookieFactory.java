package http;

import com.ning.http.client.cookie.Cookie;

import java.net.HttpCookie;
import java.util.List;

public class CookieFactory {

    /**
     * Creates a cookie from source
     *
     * @param source source cookie (string representation)
     * @return created cookie
     */
    public static Cookie create(String source) {
        List<HttpCookie> cookies = HttpCookie.parse(source);
        if (cookies == null || cookies.isEmpty())
            throw new RuntimeException("Failed to parse cookie from string: " + source);

        HttpCookie cookie = cookies.get(0);
        return Cookie.newValidCookie(cookie.getName(),
                cookie.getValue(),
                cookie.getDomain(),
                source,
                cookie.getPath(),
                Long.MAX_VALUE,
                (int)cookie.getMaxAge(),
                false,
                cookie.isHttpOnly());
    }

}
