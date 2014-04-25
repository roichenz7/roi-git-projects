package file.filters;

import java.io.File;
import java.io.FilenameFilter;

public class VideoFileFilter implements FilenameFilter {

    @Override
    public boolean accept(File f, String name) {
        return isVideoFile(name) || isSubtitleFile(name);
    }

    /**
     * @param filename filename
     * @return true if filename describes a video file
     */
    public static boolean isVideoFile(String filename) {
        return filename.endsWith(".mkv") ||
                filename.endsWith(".mp4") ||
                filename.endsWith(".avi") ||
                filename.endsWith(".mpg") ||
                filename.endsWith(".mpeg");
    }

    /**
     * @param filename filename
     * @return true if filename describes a subtitle file
     */
    public static boolean isSubtitleFile(String filename) {
        return filename.endsWith(".srt");
    }
}