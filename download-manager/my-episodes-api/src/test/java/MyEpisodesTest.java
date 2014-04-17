import org.junit.Test;
import service.MyEpisodesService;
import service.MyEpisodesServiceImpl;

public class MyEpisodesTest {

    @Test
    public void testLogin() {
        MyEpisodesService myEpisodesService = new MyEpisodesServiceImpl("roichenz7", "7roi7roi");
    }

    @Test
    public void testMarkAsAcquired() {
        MyEpisodesService myEpisodesService = new MyEpisodesServiceImpl("roichenz7", "7roi7roi");
        myEpisodesService.markAsAcquired(8353, 1, 1);
    }

    @Test
    public void testMarkAsWatched() {
        MyEpisodesService myEpisodesService = new MyEpisodesServiceImpl("roichenz7", "7roi7roi");
        myEpisodesService.markAsWatched(8353, 1, 1);
    }
}
