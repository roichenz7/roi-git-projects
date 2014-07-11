package providers.torrent;

import org.reflections.Reflections;
import providers.Provider;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TorrentProviderFactory {

    private static final Map<String, Provider> providers = createProviders();

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

    /**
     * Creates providers map according to concrete providers in "providers.torrent" package
     *
     * @return providers map
     */
    private static Map<String, Provider> createProviders() {
        Reflections reflections = new Reflections("providers.torrent");
        return reflections.getSubTypesOf(Provider.class)
                .stream()
                .filter(x -> !Modifier.isAbstract(x.getModifiers()))
                .map(x -> x.getConstructors()[0])
                .map(x -> {
                    try {
                        return (Provider) x.newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toMap(Provider::getName,
                        Function.<Provider>identity()));
    }
}
