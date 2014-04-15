package filters;

import java.io.File;
import java.io.FilenameFilter;

public class DirectoryFilter implements FilenameFilter {

    @Override
    public boolean accept(File f, String name) {
        if (name.endsWith(".db"))
            return false;

        return f.isDirectory();
    }

}
