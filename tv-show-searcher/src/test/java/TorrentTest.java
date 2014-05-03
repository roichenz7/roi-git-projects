import data.SearchResult;
import enums.Quality;
import file.FileDownloader;
import file.FileType;
import org.junit.Test;
import providers.IProvider;
import providers.torrent.TorrentProviderFactory;

import java.util.Arrays;
import java.util.List;

public class TorrentTest {

    private final List<String> acceptedOrigins = Arrays.asList("DIMENSION", "2HD", "KILLERS", "REMARKABLE", "PublicHD", "NTb");

    @Test
    public void testKat() {
        IProvider provider = TorrentProviderFactory.create("KAT");
        provider.setAcceptedOrigins(acceptedOrigins);
        List<SearchResult> results = provider.search("Arrow", 2, 18, Quality.HD_720p);
        SearchResult result = provider.getBestResult(results);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.TORRENT);
    }

    @Test
    public void testPhd() {
        IProvider provider = TorrentProviderFactory.create("PHD");
        provider.setAcceptedOrigins(acceptedOrigins);
        List<SearchResult> results = provider.search("Community", 5, 13, Quality.HD_720p);
        SearchResult result = provider.getBestResult(results);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.TORRENT);
    }

    @Test
    public void testPirateBay() {
        IProvider provider = TorrentProviderFactory.create("PirateBay");
        provider.setAcceptedOrigins(acceptedOrigins);
        List<SearchResult> results = provider.search("Game Of Thrones", 4, 1, Quality.HD_1080p);
        SearchResult result = provider.getBestResult(results);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.TORRENT);
    }
}
