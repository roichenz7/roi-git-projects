package providers.torrent.results;

import data.SearchResult;
import data.ShowData;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PirateBaySearchResult extends SearchResult {

    public PirateBaySearchResult(Element source) {
        super(source);
    }

    @Override
    protected void initialize(Element source) {
        Elements elements = source.getElementsByTag("td");

        String temp = elements.get(1).getElementsByAttribute("href").get(0).attr("href");
        String [] array = temp.split("/");
        String id = array[2];
        String showName = array[3];

        ShowData showData = ShowData.fromFilename(showName);
        initialize(showData);

        seeds = Integer.parseInt(elements.get(2).text());
        peers = Integer.parseInt(elements.get(3).text());

        downloadLink = "http://piratebaytorrents.info/" + id + "/" + showName + "." + id + ".TPB.torrent";
    }
}
