import config.PostDLConfigData;
import config.IPostDLConfigData;
import data.ShowData;
import filters.DirectoryFilter;
import filters.VideoFileFilter;
import service.MyEpisodesService;
import service.MyEpisodesServiceImpl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class PostDL implements Runnable {

    private final String configFilename;
    private final String tvShowsConfigFilename;
    private final String username;
    private final String password;
    private boolean isMarkAsAcquired;
    private MyEpisodesService myEpisodesService;

    public PostDL(String configFilename) {
        this(configFilename, null, null, null);
        this.isMarkAsAcquired = false;
    }

    public PostDL(String configFilename, String tvShowsConfigFilename, String username, String password) {
        this.configFilename = configFilename;
        this.tvShowsConfigFilename = tvShowsConfigFilename;
        this.username = username;
        this.password = password;
        this.isMarkAsAcquired = true;
    }

    @Override
    public void run() {
        IPostDLConfigData config = new PostDLConfigData();
        if (!config.parse(configFilename)) {
            System.out.println("post-dl: failed parsing configuration: " + configFilename);
            return;
        }

        File sourceDir = new File(config.sourceDir());
        if (!sourceDir.exists()) {
            System.out.println("post-dl: file does not exist: " + config.sourceDir());
            return;
        }
        if (!sourceDir.isDirectory()) {
            System.out.println("post-dl: source dir argument is not a directory: " + config.sourceDir());
            return;
        }

        String targetDirStr = config.targetDir();

        File tvShowsDir = new File(config.tvShowsDir());
        if (!tvShowsDir.exists()) {
            System.out.println("post-dl: file does not exist: " + config.tvShowsDir());
            return;
        }
        if (!tvShowsDir.isDirectory()) {
            System.out.println("post-dl: TV shows dir argument is not a directory: " + config.tvShowsDir());
            return;
        }

        System.out.println("post-dl: source dir: " + config.sourceDir());
        System.out.println("post-dl: target dir: " + config.targetDir());
        System.out.println("post-dl: TV shows dir: " + config.tvShowsDir());

        // Create list of files to move
        List<File> files = new LinkedList<>();

        // Add all relevant files to list
        System.out.println("post-dl: adding relevant files in source directory");
        addFiles(sourceDir, new VideoFileFilter(), files);

        if (files.size() == 0) {
            System.out.println("post-dl: no relevant files found");
            System.out.println("post-dl: finished");
            return;
        }
        System.out.println("post-dl: found " + files.size() + " relevant files");

        if (isMarkAsAcquired) {
            System.out.println("post-dl: logging in to my episodes, username: " + username + ", tv shows config: " + tvShowsConfigFilename);
            myEpisodesService = new MyEpisodesServiceImpl(username, password, tvShowsConfigFilename);
        }

        // Build TV shows collection
        System.out.println("post-dl: building TV shows collection");
        Collection<String> tvShows = buildTVShows(tvShowsDir);

        if (tvShows.size() == 0) {
            System.out.println("post-dl: TV shows collection is empty");
            System.out.println("post-dl: finished");
            return;
        }

        int required = files.size();
        int completed = 0;
        for (File file : files) {
            System.out.println("post-dl: processing file: " + file.getName());

            // Parse file: extract show data
            ShowData data = ShowData.fromFilename(file.getName());
            if (data.isEmpty()) {
                System.out.println("post-dl: error parsing file: " + file.getName());
                continue;
            }

            // Search for matching TV show
            if (!lookup(tvShows, data, true)) {
                System.out.println("post-dl: error matching tv show");
                continue;
            }

            System.out.println("post-dl: file parsed. " +
                    "title = " + data.getTitle() +
                    ", season = " + data.getSeasonNumber() +
                    ", episode = " + data.getEpisodeNumber());

            String targetDirStrCurr = targetDirStr + "/"
                    + data.getTitle()
                    + "/"
                    + data.getSeason();

            String targetFileStr = targetDirStrCurr
                    + "/"
                    + file.getName();

            System.out.println("post-dl: target dir for current file: " + targetDirStrCurr);
            System.out.print("post-dl: moving file...");

            File targetDir = new File(targetDirStrCurr);

            targetDir.mkdirs(); // Create directories if needed
            boolean res = file.renameTo(new File(targetFileStr)); // Rename file
            if (res) {
                System.out.println("succeeded");
                completed++;
            } else {
                System.out.println("failed");
            }

            if (isMarkAsAcquired) {
                System.out.println("post-dl: marking file as acquired: " + file.getName());
                try {
                    myEpisodesService.markAsAcquired(data.getTitle(),
                            data.getSeasonNumber(),
                            data.getEpisodeNumber());
                } catch (Exception e) {
                    System.out.println("post-dl: failed to mark file as acquired: " + file.getName() + ". " + e);
                }
            }
        }

        System.out.println("post-dl: " + completed + " of " + required + " files completed");
        System.out.println("post-dl: finished");
    }


    /**
     * Builds TV shows collection from root directory
     *
     * @param directory: TV shows directory
     * @return collection of TV shows
     */
    private Collection<String> buildTVShows(File directory) {
        Collection<String> shows = new LinkedList<>();
        for (File f : directory.listFiles(new DirectoryFilter())) {
            shows.add(f.getName());
        }
        return shows;
    }

    /**
     * Searches for given show in shows collection
     *
     * @param shows:  collection of shows to search in
     * @param show:   show data to look for
     * @param update: true if should update to exact title
     * @return true if found, false otherwise
     */
    private boolean lookup(Collection<String> shows, ShowData show, boolean update) {
        for (String temp : shows) {
            if (show.getTitle().toLowerCase().contains(temp.toLowerCase())) {
                if (update)
                    show.setTitle(temp);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds files to list according to given filer, starting from given root directory (recursively)
     *
     * @param dir:    directory to check
     * @param filter: filename filter to use
     * @param list:   list to add files to
     */
    private void addFiles(File dir, FilenameFilter filter, List<File> list) {
        for (File f : dir.listFiles()) {
            if (!f.isDirectory()) {
                if (filter.accept(f, f.getName()))
                    list.add(f);
            } else {
                addFiles(f, filter, list);
            }
        }
    }
}
