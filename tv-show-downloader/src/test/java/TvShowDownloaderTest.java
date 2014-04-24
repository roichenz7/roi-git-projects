import data.ResultData;
import enums.Quality;
import file.FileDownloader;
import org.junit.Test;
import providers.IProvider;
import providers.KatProvider;
import providers.PhdProvider;

import java.util.List;

public class TvShowDownloaderTest {

    @Test
    public void testKat() throws Exception {
        IProvider provider = new KatProvider();
        List<ResultData> results = provider.search("Arrow", 2, 18, Quality.HD_720p);
        ResultData result = provider.getBestResult(results);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString());
    }

    @Test
    public void testPhd() throws Exception {
        IProvider provider = new PhdProvider();
        List<ResultData> results = provider.search("Community", 5, 13, Quality.HD_720p);
        ResultData result = provider.getBestResult(results);
        FileDownloader.downloadFile(result.getDownloadLink(), result.toString());
    }
}