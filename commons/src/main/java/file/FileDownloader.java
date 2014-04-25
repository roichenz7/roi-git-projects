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
     * @param fileType file type
     * @throws RuntimeException
     */
    public static void downloadFile(String downloadLink, String targetFilename, FileType fileType) throws RuntimeException {
        try {
            URL website = new URL(downloadLink);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(targetFilename + "." + fileType);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file from link: " + downloadLink, e);
        }
    }
}
