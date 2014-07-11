import data.SearchQuery;
import data.SearchResult;
import enums.Quality;
import org.junit.Test;
import providers.Provider;
import providers.torrent.TorrentProviderFactory;

import java.util.Arrays;
import java.util.List;

public class TorrentTest {

    private final List<String> acceptedOrigins =
            Arrays.asList("DIMENSION",
                    "2HD",
                    "KILLERS",
                    "REMARKABLE",
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
        Provider provider = TorrentProviderFactory.create("KAT");
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("Orange Is The New Black", 2, 10, Quality.HD_720p);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        provider.download(result);
    }

    @Test
    public void testKatProxy() {
        Provider provider = TorrentProviderFactory.create("KATProxy");
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("Graceland", 2, 3, Quality.HD_720p);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        provider.download(result);
    }

    @Test
    public void testPhd() {
        Provider provider = TorrentProviderFactory.create("PHD");
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("The 100", 1, 11, Quality.HD);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        provider.download(result);
    }

    @Test
    public void testPirateBay() {
        Provider provider = TorrentProviderFactory.create("PirateBay");
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("Game Of Thrones", 4, 1, Quality.HD_1080p);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        provider.download(result);
    }
}
