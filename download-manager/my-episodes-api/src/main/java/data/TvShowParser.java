package data;

import data.parser.AbstractDataParser;
import data.serializers.ISerializer;
import data.serializers.XmlSerializer;
import org.w3c.dom.Node;

public class TvShowParser extends AbstractDataParser<TvShowData> implements ITvShowParser {

    @Override
    public TvShowData getShowById(int id) {
        return getFirst(s -> s.getId() == id);
    }

    @Override
    public TvShowData getShowByName(String name) {
        return getFirst(s -> s.getName().equals(name));
    }

    @Override
    protected ISerializer<TvShowData> getSerializer() {
        return new XmlSerializer<TvShowData>() {
            @Override
            protected String getElementTagName() {
                return "tv_show";
            }

            @Override
            protected TvShowData createElement(Node node) {
                return new TvShowData(node);
            }
        };
    }
}
