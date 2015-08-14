package providers;

public abstract class ProviderBase implements Provider {

    private final String baseUrl;

    protected ProviderBase(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public final String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public final String getBaseUrl() {
        return baseUrl;
    }
}
