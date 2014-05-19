import data.SearchQuery;
import data.SearchResult;
import enums.Quality;
import file.FileDownloader;
import file.FileType;
import org.junit.Test;
import providers.Provider;
import providers.torrent.TorrentProviderFactory;

import java.util.Arrays;
import java.util.List;

public class TorrentTest {

    private final List<String> acceptedOrigins = Arrays.asList("DIMENSION", "2HD", "KILLERS", "REMARKABLE", "PublicHD", "NTb");

    @Test
    public void testKat() {
        Provider provider = TorrentProviderFactory.create("KAT");
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("Arrow", 2, 23, Quality.HD_720p);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.TORRENT);
    }

    @Test
    public void testPhd() {
        Provider provider = TorrentProviderFactory.create("PHD");
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("The Mentalist", 6, 18, Quality.HD_720p);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.TORRENT);
    }

    @Test
    public void testPirateBay() {
        Provider provider = TorrentProviderFactory.create("PirateBay");
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("Game Of Thrones", 4, 1, Quality.HD_1080p);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.TORRENT);
    }
}
