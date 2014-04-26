package providers.srt;

import data.RequestData;
import data.ResultData;

import java.util.ArrayList;
import java.util.List;

public class SubsCenterProvider implements ISrtProvider {

    @Override
    public String getName() {
        return "SubsCenter";
    }

    @Override
    public String getBaseUrl() {
        return "http://subscenter.cinemast.com";
    }

    @Override
    public List<ResultData> search(RequestData requestData) {
        return new ArrayList<>(); // TODO
    }
}
