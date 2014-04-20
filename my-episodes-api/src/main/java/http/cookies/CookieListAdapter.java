package http.cookies;

import com.ning.http.client.cookie.Cookie;
import collectors.DefaultCollector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CookieListAdapter extends ArrayList<Cookie> {

    private String listAsString = "";

    @Override
    public String toString() {
        listAsString = "";
        forEach(c -> listAsString += c + "; ");
        return listAsString;
    }

    /**
     * @return collector
     */
    public static Collector<Cookie, ?, List<Cookie>> toCookieList() {
        return new DefaultCollector<>((Supplier<List<Cookie>>) CookieListAdapter::new, List::add,
                (left, right) -> { left.addAll(right); return left; },
                Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH)));
    }
}
