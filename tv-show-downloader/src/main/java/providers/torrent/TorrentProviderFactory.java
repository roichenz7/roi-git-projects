package providers.torrent;

import providers.IProvider;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TorrentProviderFactory {

    private static final Map<String, IProvider> providers = Arrays.asList(new KatProvider(),
            new PhdProvider(), new PirateBayProvider())
            .stream()
            .collect(Collectors.toMap(IProvider::getName,
                    Function.<IProvider>identity()));

    /**
     * Creates provider according to its name
     *
     * @param name provider name
     * @return provider
     */
    public static IProvider create(String name) {
        return providers.get(name);
    }
}
