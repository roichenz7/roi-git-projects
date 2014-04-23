package file;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public final class FileDownloader {

    /**
     * Downloads file according to given download link
     *
     * @param downloadLink download link
     * @param targetFilename target file name
     * @throws Exception
     */
    public static void downloadFile(String downloadLink, String targetFilename) throws Exception {
        URL website = new URL(downloadLink);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(targetFilename + ".torrent");
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }
}
