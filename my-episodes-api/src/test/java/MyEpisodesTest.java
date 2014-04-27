import data.TvShowData;
import org.junit.Test;
import service.MyEpisodesService;
import service.MyEpisodesServiceImpl;

import java.util.Collection;
import java.util.Map;

public class MyEpisodesTest {

    @Test
    public void testLogin() {
        MyEpisodesService myEpisodesService = new MyEpisodesServiceImpl("roichenz7", "***");
    }

    @Test
    public void testGetMyShows() {
        MyEpisodesService myEpisodesService = new MyEpisodesServiceImpl("roichenz7", "***");
        Map<Integer, String> myShows = myEpisodesService.getMyShows();
        myShows.forEach((l, r) -> System.out.printf("<%d, %s>\n", l, r));
    }

    @Test
    public void testGetStatus() {
        MyEpisodesService myEpisodesService = new MyEpisodesServiceImpl("roichenz7", "***", "config/tv-shows.xml");
        Collection<TvShowData> status = myEpisodesService.getStatus();
        status.forEach(System.out::println);
    }

    @Test
    public void testMarkAsAcquired() {
        MyEpisodesService myEpisodesService = new MyEpisodesServiceImpl("roichenz7", "***");
        myEpisodesService.markAsAcquired(8353, 1, 1);
    }

    @Test
    public void testMarkAsAcquiredWithName() {
        MyEpisodesService myEpisodesService = new MyEpisodesServiceImpl("roichenz7", "***", "config/tv-shows.xml");
        myEpisodesService.markAsAcquired("Suits", 1, 1);
    }

    @Test
    public void testMarkAsWatched() {
        MyEpisodesService myEpisodesService = new MyEpisodesServiceImpl("roichenz7", "***");
        myEpisodesService.markAsWatched(8353, 1, 1);
    }

    @Test
    public void testMarkAsWatchedWithName() {
        MyEpisodesService myEpisodesService = new MyEpisodesServiceImpl("roichenz7", "***", "config/tv-shows.xml");
        myEpisodesService.markAsWatched("Suits", 1, 1);
    }
}
