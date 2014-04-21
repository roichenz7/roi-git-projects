import data.ResultData;
import enums.Quality;
import org.junit.Test;
import providers.*;

import java.util.List;

public class PreDLTest {

    @Test
    public void testKat() {
        IProvider provider = new KatProvider();
        List<ResultData> results = provider.search("Arrow", 2, 18, Quality.HD_720p);
        ResultData result = provider.getBestResult(results);
        provider.downloadFile(result);
    }

    @Test
    public void testPhd() {
        IProvider provider = new PhdProvider();
        List<ResultData> results = provider.search("Arrow", 2, 17, Quality.HD_720p);
        ResultData result = provider.getBestResult(results);
        provider.downloadFile(result);
    }
}
