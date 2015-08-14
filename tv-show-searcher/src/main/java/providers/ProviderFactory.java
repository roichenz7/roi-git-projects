package providers;

import org.reflections.Reflections;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

public class ProviderFactory {

    private static final Set<Class<? extends Provider>> providers = createProviders();

    /**
     * Creates provider according to its config
     *
     * @param config provider config
     * @return provider
     */
    public static Provider create(Node config) {
        final NamedNodeMap attributes = config.getAttributes();
        return create(attributes.getNamedItem("name").getNodeValue(), attributes.getNamedItem("url").getNodeValue());
    }

    /**
     * Creates provider according to its name & url
     *
     * @param name provider name
     * @param url  provider url
     * @return provider
     */
    public static Provider create(String name, String url) {
        final Class<? extends Provider> providerClass = providers.stream()
                .filter(c -> c.getSimpleName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to find provider with name: " + name));

        try {
            return providerClass.getConstructor(String.class).newInstance(url);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create provider with name: " + name, e);
        }
    }

    private static Set<Class<? extends Provider>> createProviders() {
        Reflections reflections = new Reflections("providers");
        return reflections.getSubTypesOf(Provider.class)
                .stream()
                .filter(x -> !Modifier.isAbstract(x.getModifiers()))
                .collect(Collectors.toSet());
    }
}
