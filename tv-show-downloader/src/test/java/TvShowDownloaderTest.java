import data.ResultData;
import enums.Quality;
import file.FileDownloader;
import file.FileType;
import org.junit.Test;
import providers.IProvider;
import providers.srt.SubsCenterProvider;
import providers.srt.SubtitleProvider;
import providers.srt.TorecProvider;
import providers.torrent.KatProvider;
import providers.torrent.PhdProvider;

import java.util.List;

public class TvShowDownloaderTest {

    @Test
    public void testKat() {
        IProvider provider = new KatProvider();
        List<ResultData> results = provider.search("Arrow", 2, 18, Quality.HD_720p);
        ResultData result = provider.getBestResult(results);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.TORRENT);
    }

    @Test
    public void testPhd() {
        IProvider provider = new PhdProvider();
        List<ResultData> results = provider.search("Community", 5, 13, Quality.HD_720p);
        ResultData result = provider.getBestResult(results);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.TORRENT);
    }

    @Test
    public void testSubtitle() {
        IProvider provider = new SubtitleProvider("email", "password");
        List<ResultData> results = provider.search("The Mentalist", 6, 18, Quality.HD_720p);
        ResultData result = provider.getBestResult(results);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.SRT);
    }

    @Test
    public void testSubsCenter() {
        IProvider provider = new SubsCenterProvider();
        List<ResultData> results = provider.search("Modern Family", 5, 20, Quality.HD_720p);
        ResultData result = provider.getBestResult(results);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.SRT);
    }

    @Test
    public void testTorec() {
        IProvider provider = new TorecProvider();
        List<ResultData> results = provider.search("Californication", 7, 2, Quality.HD_720p);
        ResultData result = provider.getBestResult(results);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.SRT);
    }
}
