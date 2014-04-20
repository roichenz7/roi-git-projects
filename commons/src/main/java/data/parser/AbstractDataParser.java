package data.parser;

import data.serializers.ISerializer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractDataParser<T> implements IDataParser<T> {

    private List<T> data = new ArrayList<>();

    @Override
    public final T getFirst(Predicate<T> filter) {
        for (T item : data) {
            if (filter.test(item)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public final List<T> getAll(final Predicate<T> filter) {
        return data.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    @Override
    public final List<T> getAll() {
        return data;
    }

    @Override
    public final void add(T item) {
        data.add(item);
    }

    @Override
    public final void deserialize(String filename) {
        getSerializer().deserialize(filename, this);
    }

    @Override
    public final Iterator<T> iterator() {
        return data.iterator();
    }

    /**
     * @return serializer object
     */
    protected abstract ISerializer<T> getSerializer();
}
