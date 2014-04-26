import data.ResultData;
import enums.Quality;
import file.FileDownloader;
import file.FileType;
import org.junit.Test;
import providers.IProvider;
import providers.torrent.TorrentProviderFactory;

import java.util.List;

public class TorrentTest {

    @Test
    public void testKat() {
        IProvider provider = TorrentProviderFactory.create("KAT");
        List<ResultData> results = provider.search("Arrow", 2, 18, Quality.HD_720p);
        ResultData result = provider.getBestResult(results);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.TORRENT);
    }

    @Test
    public void testPhd() {
        IProvider provider = TorrentProviderFactory.create("PHD");
        List<ResultData> results = provider.search("Community", 5, 13, Quality.HD_720p);
        ResultData result = provider.getBestResult(results);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.TORRENT);
    }

    @Test
    public void testPirateBay() {
        IProvider provider = TorrentProviderFactory.create("PirateBay");
        List<ResultData> results = provider.search("Game Of Thrones", 4, 1, Quality.HD_1080p);
        ResultData result = provider.getBestResult(results);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString(), FileType.TORRENT);
    }
}
