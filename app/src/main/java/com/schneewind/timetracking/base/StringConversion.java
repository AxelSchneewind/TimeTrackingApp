package com.schneewind.timetracking.base;

import java.util.Collection;

public interface StringConversion<T> {
    String convertToString(T object);
    T convertFromString(String string);

    Collection<String> getReservedStrings();
}