package providers.torrent;

import providers.Provider;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TorrentProviderFactory {

    private static final Map<String, Provider> providers = Arrays.asList(new KatProvider(),
            new PhdProvider(), new PirateBayProvider())
            .stream()
            .collect(Collectors.toMap(Provider::getName,
                    Function.<Provider>identity()));

    /**
     * Creates provider according to its name
     *
     * @param name provider name
     * @return provider
     */
    public static Provider create(String name) {
        if (!providers.containsKey(name))
            throw new IllegalArgumentException("No such provider: " + name);

        return providers.get(name);
    }
}
