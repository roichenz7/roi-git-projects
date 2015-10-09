import data.TvShowData;
import org.junit.Test;
import service.MyEpisodesService;
import service.TraktServiceImpl;

import java.util.Collection;
import java.util.Map;

public class TraktTest {

    @Test
    public void testGetMyShows() {
        MyEpisodesService myEpisodesService = new TraktServiceImpl("roichenz7", "***");
        Map<Integer, String> myShows = myEpisodesService.getMyShows();
        myShows.forEach((l, r) -> System.out.printf("<%d, %s>\n", l, r));
    }

    @Test
    public void testGetStatus() {
        MyEpisodesService myEpisodesService = new TraktServiceImpl("roichenz7", "***");
        Collection<TvShowData> status = myEpisodesService.getStatus();
        status.forEach(System.out::println);
    }
}
