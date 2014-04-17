import org.junit.Test;
import service.MyEpisodesService;
import service.MyEpisodesServiceImpl;

public class MyEpisodesTest {

    @Test
    public void testLogin() {
        MyEpisodesService myEpisodesService = new MyEpisodesServiceImpl("roichenz7", "7roi7roi");
    }
}
