import file.FileDownloader;
import file.FileType;
import org.junit.Test;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class FileDownloaderTest {

    @Test
    public void test1() { // KAT link
        FileDownloader.downloadFile("http://torcache.net/torrent/1FC4946F9C9EC781A4FC917E0528918788ECC454.torrent", "test1", FileType.TORRENT,
                x -> {
                    try {
                        return new GZIPInputStream(x);
                    } catch (IOException e) {
                        return null;
                    }
                });
    }

    @Test
    public void test2() { // PirateBay link
        FileDownloader.downloadFile("http://piratebaytorrents.info/9908788/Game.Of.Thrones.S04E01.1080p.HDTV.DD5.1.x264-PublicHD.9908788.TPB.torrent", "test2", FileType.TORRENT);
    }
}
