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
        List<SearchResult> results = provider.search("Modern Family", 5, 20, Quality.HD_720p);
        SearchResult result = provider.getBestResult(results);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.SRT);
    }

    @Test
    public void testSubtitle() {
        IProvider provider = new SubtitleProvider("email", "password");
        provider.setAcceptedOrigins(acceptedOrigins);
        List<SearchResult> results = provider.search("The Mentalist", 6, 18, Quality.HD_720p);
        SearchResult result = provider.getBestResult(results);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.SRT);
    }

    @Test
    public void testTorec() {
        IProvider provider = new TorecProvider();
        provider.setAcceptedOrigins(acceptedOrigins);
        List<SearchResult> results = provider.search("Californication", 7, 2, Quality.HD_720p);
        SearchResult result = provider.getBestResult(results);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.SRT);
    }
}
