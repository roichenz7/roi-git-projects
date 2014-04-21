package providers;

import data.ResultData;
import enums.Quality;

import java.util.ArrayList;
import java.util.List;

public class PhdProvider implements IProvider {

    @Override
    public final String getName() {
        return "PHD";
    }

    @Override
    public final String getBaseUrl() {
        return null;
    }

    @Override
    public List<ResultData> search(String tvShowName, int season, int episode, Quality quality) {
        return new ArrayList<>();
    }

    @Override
    public void downloadFile(ResultData resultData) {
    }
}
