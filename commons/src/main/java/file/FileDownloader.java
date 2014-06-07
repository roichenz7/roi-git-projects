package file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Function;

public final class FileDownloader {

    private FileDownloader() {}

    /**
     * Downloads file according to given download link
     *
     * @param downloadLink download link
     * @param targetPath target path
     * @param targetFilename target file name
     * @param fileType file type
     */
    public static void downloadFile(String downloadLink,
                                    String targetPath,
                                    String targetFilename,
                                    FileType fileType) {
        downloadFile(downloadLink,
                targetPath,
                targetFilename,
                fileType,
                Function.<InputStream>identity());
    }

    /**
     * Downloads file according to given download link
     *
     * @param downloadLink download link
     * @param targetPath target path
     * @param targetFilename target file name
     * @param fileType file type
     * @param inputStreamFunction input stream mapping function
     */
    public static void downloadFile(String downloadLink,
                                    String targetPath,
                                    String targetFilename,
                                    FileType fileType,
                                    Function<InputStream, InputStream> inputStreamFunction) {
        try {
            URL url = new URL(downloadLink);
            ReadableByteChannel rbc = Channels.newChannel(inputStreamFunction.apply(url.openStream()));

            File targetFile = new File(targetPath + "/" + targetFilename + "." + fileType);
            if (targetFile.exists())
                throw new IOException("file already exists: " + targetFile.getName());

            File tempFile = new File(targetFilename + "." + fileType);
            FileOutputStream fos = new FileOutputStream(tempFile);

            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.flush();
            fos.close();

            tempFile.renameTo(targetFile);
        } catch (Exception e) {
            throw new RuntimeException("Failed to download file from link: " + downloadLink, e);
        }
    }
}
