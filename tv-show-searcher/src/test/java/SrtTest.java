import data.SearchQuery;
import data.SearchResult;
import enums.Quality;
import org.junit.Test;
import providers.Provider;
import providers.srt.SubsCenterProvider;
import providers.srt.SubtitleProvider;
import providers.srt.TorecProvider;

import java.util.Arrays;
import java.util.List;

public class SrtTest {

    private final List<String> acceptedOrigins = Arrays.asList("DIMENSION", "2HD", "KILLERS", "REMARKABLE", "PublicHD", "NTb");

    @Test
    public void testSubsCenter() {
        Provider provider = new SubsCenterProvider();
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("Modern Family", 5, 20, Quality.HD_720p);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        provider.download(result);
    }

    @Test
    public void testSubtitle() {
        Provider provider = new SubtitleProvider("email", "password");
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("The Mentalist", 6, 18, Quality.HD_720p);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        provider.download(result);
    }

    @Test
    public void testTorec() {
        Provider provider = new TorecProvider();
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("Californication", 7, 2, Quality.HD_720p);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        provider.download(result);
    }
}
