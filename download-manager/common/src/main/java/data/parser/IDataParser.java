package data.parser;

import java.util.List;
import java.util.function.Predicate;

public interface IDataParser<T> extends Iterable<T> {

    /**
     * Attempts to find data according to given filter
     *
     * @param filter filter to use
     * @return first applicable data if found, null otherwise
     */
    T getFirst(Predicate<T> filter);

    /**
     * Attempts to find data according to given filter
     *
     * @param filter filter to use
     * @return all applicable data if found, null otherwise
     */
    List<T> getAll(Predicate<T> filter);

    /**
     * @return all data in this mapper
     */
    List<T> getAll();

    /**
     * Adds given item to this data mapper
     *
     * @param item item to add
     */
    void add(T item);

    /**
     * Performs de-serialization from filename
     *
     * @param filename source filename
     */
    void deserialize(String filename);
}
