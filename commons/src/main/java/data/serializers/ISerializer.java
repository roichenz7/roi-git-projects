package data.serializers;

import data.parser.IDataParser;

public interface ISerializer<T> {

    /**
     * Performs de-serialization from filename to given data parser
     *
     * @param filename source filename
     * @param dataParser data parser
     */
    void deserialize(String filename, IDataParser<T> dataParser);
}
