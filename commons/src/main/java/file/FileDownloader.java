package file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public final class FileDownloader {

    private FileDownloader() {}

    /**
     * Downloads file according to given download link
     *
     * @param downloadLink download link
     * @param targetFilename target file name
     * @param fileType file type
     */
    public static void downloadFile(String downloadLink, String targetFilename, FileType fileType) {
        try {
            URL website = new URL(downloadLink);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            File file = new File(targetFilename + "." + fileType);
            if (file.exists())
                throw new IOException("file already exists: " + file.getName());

            FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file from link: " + downloadLink, e);
        }
    }
}
