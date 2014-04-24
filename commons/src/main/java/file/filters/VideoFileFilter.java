package file.filters;

import java.io.File;
import java.io.FilenameFilter;

public class VideoFileFilter implements FilenameFilter {

    @Override
    public boolean accept(File f, String name) {
        if (name.endsWith(".mkv"))
            return true;

        if (name.endsWith(".srt"))
            return true;

        if (name.endsWith(".mp4"))
            return true;

        if (name.endsWith(".avi"))
            return true;

        if (name.endsWith(".mpg"))
            return true;

        if (name.endsWith(".mpeg"))
            return true;

        return false;
    }

}