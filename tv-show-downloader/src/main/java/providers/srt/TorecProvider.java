package providers.srt;

import data.RequestData;
import data.ResultData;

import java.util.ArrayList;
import java.util.List;

public class TorecProvider implements ISrtProvider {

    @Override
    public String getName() {
        return "Torec";
    }

    @Override
    public String getBaseUrl() {
        return "http://www.torec.net/";
    }

    @Override
    public List<ResultData> search(RequestData requestData) {
        return new ArrayList<>(); // TODO
    }
}
