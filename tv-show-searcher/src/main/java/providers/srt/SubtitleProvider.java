package providers.srt;

import data.RequestData;
import data.ResultData;

import java.util.ArrayList;
import java.util.List;

public class SubtitleProvider implements ISrtProvider {

    public SubtitleProvider(final String email, final String password) {
        login(email, password);
    }

    @Override
    public String getName() {
        return "Subtitle";
    }

    @Override
    public String getBaseUrl() {
        return "http://www.subtitle.co.il";
    }

    @Override
    public List<ResultData> search(RequestData requestData) {
        return new ArrayList<>(); // TODO
    }

    /**
     * Performs login to subtitle
     *
     * @param email email
     * @param password password
     */
    private void login(final String email, final String password) {
        // TODO
    }
}
