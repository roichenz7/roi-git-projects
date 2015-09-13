package file;

import http.DefaultHttpRequestBuilder;
import http.HttpMethod;
import http.HttpResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        downloadFile(downloadLink, downloadFunction(), targetPath, targetFilename, fileType);
    }

    /**
     * Downloads file according to given download link
     *
     * @param downloadLink download link
     * @param downloadFunction download function
     * @param targetPath target path
     * @param targetFilename target file name
     * @param fileType file type
     */
    public static void downloadFile(String downloadLink,
                                    Function<String, InputStream> downloadFunction,
                                    String targetPath,
                                    String targetFilename,
                                    FileType fileType) {
        try {
            ReadableByteChannel rbc = Channels.newChannel(downloadFunction.apply(downloadLink));

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

    public static Function<String, InputStream> downloadFunction() {
        return downloadLink -> {
            HttpResponse response = new DefaultHttpRequestBuilder(HttpMethod.GET, downloadLink)
                    .withHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0")
                    .withAccept("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .withHeader("Accept-Language", "en-US,en;q=0.5")
                    .withHeader("Accept-Encoding", "gzip, deflate")
                    .execute();
            return FileUtils.gzipIfNeeded(response.getBodyAsStream());
        };
    }
}
