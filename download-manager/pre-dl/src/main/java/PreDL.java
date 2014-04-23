import data.EpisodeData;
import data.ResultData;
import enums.Quality;
import file.FileDownloader;
import providers.IProvider;
import providers.PhdProvider;
import service.MyEpisodesService;
import service.MyEpisodesServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class PreDL implements Runnable {

    private final String configFilename;
    private final String tvShowsConfigFilename;
    private final String username;
    private final String password;

    private final IProvider provider;

    public PreDL(String configFilename, String tvShowsConfigFilename, String username, String password) {
        this.configFilename = configFilename; // TODO: ???
        this.tvShowsConfigFilename = tvShowsConfigFilename;
        this.username = username;
        this.password = password;

        this.provider = new PhdProvider(); // TODO: provider from config
    }

    @Override
    public void run() {
        System.out.println("pre-dl: logging in to my episodes, username: " + username + ", tv shows config: " + tvShowsConfigFilename);
        MyEpisodesService myEpisodesService = new MyEpisodesServiceImpl(username, password, tvShowsConfigFilename);

        System.out.println("pre-dl: getting list of episodes to acquire");
        List<EpisodeData> episodesToAcquire = myEpisodesService.getStatus()
                .stream()
                .filter(x -> !x.getUnacquiredEpisodes().isEmpty())
                .flatMap(x -> x.getUnacquiredEpisodes().stream())
                .collect(Collectors.toList());

        if (episodesToAcquire.isEmpty()) {
            System.out.println("pre-dl: there are no un-acquired episodes");
            return;
        }

        System.out.println("pre-dl: un-acquired episodes:");
        episodesToAcquire.forEach(System.out::println);

        System.out.println("pre-dl: processing un-acquired episodes");
        episodesToAcquire.forEach(e -> {
            System.out.println("pre-dl: searching for:" + e);

            List<ResultData> results = provider.search(e.getTvShowName(), e.getSeason(), e.getEpisode(), Quality.HD_720p); // TODO: quality from config
            System.out.println("pre-dl: got " + results.size() + " results");

            System.out.println("pre-dl: getting best results");
            ResultData result = provider.getBestResult(results);

            System.out.println("pre-dl: downloading file: " + result);
            FileDownloader.downloadFile(result.getDownloadLink(), result.toString());
        });
    }
}
