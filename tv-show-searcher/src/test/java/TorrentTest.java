import data.SearchQuery;
import data.SearchResult;
import enums.Quality;
import org.junit.Test;
import providers.Provider;
import providers.ProviderFactory;

import java.util.Arrays;
import java.util.List;

public class TorrentTest {

    private final List<String> acceptedOrigins =
            Arrays.asList("DIMENSION",
                    "2HD",
                    "KILLERS",
                    "REMARKABLE",
                    "IMMERSE",
                    "PublicHD",
                    "EXCELLENCE",
                    "BAJSKORV",
                    "BATV",
                    "DAA",
                    "NTb",
                    "LOL",
                    "AFG",
                    "FoV");

    @Test
    public void testKat() {
        Provider provider = ProviderFactory.create("KatProvider", "http://kat.cr");
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("Orange Is The New Black", 2, 10, Quality.HD_720p);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        provider.download(result);
    }

    @Test
    public void testKatProxy() {
        Provider provider = ProviderFactory.create("KatProxyProvider", "https://kickass.unblocked.pw");
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("Graceland", 2, 3, Quality.HD_720p);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        provider.download(result);
    }

    @Test
    public void testPhd() {
        Provider provider = ProviderFactory.create("PhdProvider", "");
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("The 100", 1, 11, Quality.HD);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        provider.download(result);
    }

    @Test
    public void testPirateBay() {
        Provider provider = ProviderFactory.create("PirateBayProvider", "http://thepiratebay.se");
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("Game Of Thrones", 4, 1, Quality.HD_1080p);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        provider.download(result);
    }

    @Test
    public void testRarbg() {
        Provider provider = ProviderFactory.create("RarbgProvider", "http://rarbg.to");
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("Silicon Valley", 2, 2, Quality.HD_720p);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        provider.download(result);
    }
}
