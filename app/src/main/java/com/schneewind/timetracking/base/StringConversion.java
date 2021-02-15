package com.schneewind.timetracking.base;

public interface StringConversion<T> {
    String convertToString(T object);
    T convertFromString(String string);

    char getSeparator();
}
