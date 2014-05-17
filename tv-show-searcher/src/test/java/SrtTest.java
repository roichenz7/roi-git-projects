import data.SearchQuery;
import data.SearchResult;
import enums.Quality;
import file.FileDownloader;
import file.FileType;
import org.junit.Test;
import providers.IProvider;
import providers.srt.SubsCenterProvider;
import providers.srt.SubtitleProvider;
import providers.srt.TorecProvider;

import java.util.Arrays;
import java.util.List;

public class SrtTest {

    private final List<String> acceptedOrigins = Arrays.asList("DIMENSION", "2HD", "KILLERS", "REMARKABLE", "PublicHD", "NTb");

    @Test
    public void testSubsCenter() {
        IProvider provider = new SubsCenterProvider();
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("Modern Family", 5, 20, Quality.HD_720p);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.SRT);
    }

    @Test
    public void testSubtitle() {
        IProvider provider = new SubtitleProvider("email", "password");
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("The Mentalist", 6, 18, Quality.HD_720p);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.SRT);
    }

    @Test
    public void testTorec() {
        IProvider provider = new TorecProvider();
        provider.setAcceptedOrigins(acceptedOrigins);

        SearchQuery query = new SearchQuery("Californication", 7, 2, Quality.HD_720p);
        List<SearchResult> results = provider.search(query);

        SearchResult result = provider.getBestResult(results, query);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.SRT);
    }
}
