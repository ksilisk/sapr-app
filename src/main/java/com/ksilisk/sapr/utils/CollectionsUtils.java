package com.ksilisk.sapr.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Comparator.comparingInt;

public class CollectionsUtils {
    private CollectionsUtils() {
        throw new IllegalStateException("This is Util class. Can't create object");
    }

    public static <T> boolean equalLists(List<T> collection, List<T> collection1) {
        if (collection == collection1) return true;
        if (collection == null || collection1 == null || collection.size() != collection1.size()) return false;
        collection = new ArrayList<>(collection);
        collection1 = new ArrayList<>(collection1);
        collection.sort(comparingInt(Objects::hashCode));
        collection1.sort(comparingInt(Objects::hashCode));
        return collection.equals(collection1);
    }

}
