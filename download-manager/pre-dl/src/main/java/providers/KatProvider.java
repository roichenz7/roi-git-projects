package providers;

import data.ResultData;
import enums.Quality;

import java.util.List;

public class KatProvider implements IProvider {

    @Override
    public String getName() {
        return "KAT";
    }

    @Override
    public List<ResultData> search(String tvShowName, int season, int episode, Quality quality) {
        return null;
    }

    @Override
    public void downloadFile(ResultData resultData) {
    }
}
