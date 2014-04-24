import config.PreDLConfigData;
import config.IPreDLConfigData;
import data.EpisodeData;
import data.ResultData;
import enums.Quality;
import file.FileDownloader;
import providers.IProvider;
import providers.PhdProvider;
import service.MyEpisodesService;
import service.MyEpisodesServiceImpl;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class PreDL implements Runnable {

    private final String configFilename;
    private final String tvShowsConfigFilename;
    private final String username;
    private final String password;

    private final IProvider provider;

    public PreDL(String configFilename, String tvShowsConfigFilename, String username, String password) {
        this.configFilename = configFilename;
        this.tvShowsConfigFilename = tvShowsConfigFilename;
        this.username = username;
        this.password = password;

        this.provider = new PhdProvider(); // TODO: provider from config
    }

    @Override
    public void run() {
        IPreDLConfigData config = new PreDLConfigData();
        if (!config.parse(configFilename)) {
            System.out.println("pre-dl: failed parsing configuration: " + configFilename);
            return;
        }

        final File downloadDir = new File(config.downloadDir());
        if (!downloadDir.exists()) {
            System.out.println("pre-dl: file does not exist: " + config.downloadDir());
            return;
        }
        if (!downloadDir.isDirectory()) {
            System.out.println("pre-dl: download dir argument is not a directory: " + config.downloadDir());
            return;
        }

        System.out.println(config);

        System.out.println("pre-dl: logging in to my episodes, username: " + username + ", tv shows config: " + tvShowsConfigFilename);
        MyEpisodesService myEpisodesService = new MyEpisodesServiceImpl(username, password, tvShowsConfigFilename);

        System.out.println("pre-dl: getting list of episodes to acquire");
        List<EpisodeData> episodesToAcquire = myEpisodesService.getStatus()
                .stream()
                .filter(x -> !config.ignoredShows().contains(x))
                .flatMap(x -> x.getUnAcquiredEpisodes().stream())
                .collect(Collectors.toList());

        if (episodesToAcquire.isEmpty()) {
            System.out.println("pre-dl: there are no un-acquired episodes");
            return;
        }

        System.out.println("pre-dl: un-acquired episodes:");
        episodesToAcquire.forEach(System.out::println);

        System.out.println("pre-dl: processing un-acquired episodes");
        episodesToAcquire.forEach(e -> {
            Quality quality = config.getTvShowQuality(e.getTvShowData().getId());
            System.out.println("pre-dl: searching for:" + e + " [" + quality + "]");

            List<ResultData> results = provider.search(e.getTvShowName(),
                    e.getSeason(),
                    e.getEpisode(),
                    quality);

            System.out.println("pre-dl: got " + results.size() + " results");

            System.out.println("pre-dl: getting best result");
            ResultData result = provider.getBestResult(results);

            System.out.println("pre-dl: downloading file: " + result);
            String filename = downloadDir.getPath() + "/" + result.toString();
            FileDownloader.downloadFile(result.getDownloadLink(), filename);
            System.out.println("pre-dl: file downloaded: " + filename);
        });
    }
}
