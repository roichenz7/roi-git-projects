package file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public final class FileUtils {

    private FileUtils() {}

    /**
     * Returns all file names in given directory
     *
     * @param directory source directory
     * @return child file names
     * @throws IOException
     */
    public static Set<String> getFilesInPath(File directory) throws IOException {
        return getFilesInPath(directory.toPath());
    }

    /**
     * Returns all file names in given directory
     *
     * @param directory source directory
     * @return child file names
     * @throws IOException
     */
    public static Set<String> getFilesInPath(Path directory) throws IOException {
        return Files.list(directory)
                .map(x -> x.toFile().getName())
                .collect(Collectors.toSet());
    }

    /**
     * Returns input stream (gzipped or original)
     *
     * @param source source input stream
     * @return input stream (gzipped or original)
     */
    public static InputStream gzipIfNeeded(InputStream source) {
        try {
            return new GZIPInputStream(source);
        } catch (IOException e) {
            return source;
        }
    }
}
