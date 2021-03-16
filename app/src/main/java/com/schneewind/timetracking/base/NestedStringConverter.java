package com.schneewind.timetracking.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * A class that implements StringConversion functionality. It can contain StringConverters that are
 * used to convert nested strings.
 * @param <T> the type of object to be converted by this class
 */
public abstract class NestedStringConverter<T> implements  StringConversion<T> {
    private final char separator;
    private final char goDeeper;
    private final char goUp;

    protected final List<StringConversion> nestedConverters;


    /**
     * getter for the seperating character between the Strings of different fields
     * @return
     */
    public char getSeparator() {
        return separator;
    }

    /**
     * getter for the character used to indicate the beginning of a deeper layer in nested strings
     * @return
     */
    public char getGoDeeper() {
        return goDeeper;
    }

    /**
     * getter for the character used to indicate the end of a deeper layer in nested strings
     * @return
     */
    public char getGoUp() {
        return goUp;
    }

    /**
     * getter for the converters that are responsible for converting nested strings
     * @return
     */
    public List<StringConversion> getNestedConverters() {
        return nestedConverters;
    }


    /**
     * constructor for a default converter using brackets and semicolons for separation
     */
    protected NestedStringConverter() {
        this.separator = ';';
        this.goDeeper = '{';
        this.goUp = '}';
        this.nestedConverters = new ArrayList<>();
    }

    /**
     * constructor for a default converter using brackets and semicolons for separation
     */
    protected NestedStringConverter(List<StringConversion> nestedConverters) {
        this.separator = ';';
        this.goDeeper = '{';
        this.goUp = '}';
        this.nestedConverters = nestedConverters;
    }


    /**
     * a function that returns all strings that have been reserved by the converter. These strings
     * may not appear within the content that ist converted to a string.
     * @return a collection containing all strings that are reserved by this converter and the included converters
     */
    @Override
    public Collection<String> getReservedStrings() {
        Collection<String> result = new HashSet<String>();

        result.add(String.valueOf(separator));
        result.add(String.valueOf(goDeeper));
        result.add(String.valueOf(goUp));

        for(StringConversion converter : nestedConverters){
            result.addAll(converter.getReservedStrings());
        }

        return result;
    }


    /**
     * A function that removes all substrings from the given String that match the reserved strings.
     * @param content the String to sanitize
     * @return a new String that does not contain any reserved Strings
     */
    public String sanitize(Object content){
        StringBuilder sanitizedString = new StringBuilder(content.toString());

        Collection<String> reservedStrings = getReservedStrings();

        for(final String reservedString : reservedStrings){
            while(sanitizedString.toString().contains(reservedString)){
                int index = sanitizedString.indexOf(reservedString);
                sanitizedString.replace(index, index + reservedString.length(), "");
            } //FIXME
        }

        return sanitizedString.toString();
    }

    /**
     * converts the given object to a string.
     * This is achieved by calling the abstract toStringSequence operation and appending the resulting
     * strings, seperated by this objects seperator char.
     * @param object the object to convert
     * @return a string that contains all the needed information of the object
     */
    @Override
    public String convertToString(T object) {
        StringBuilder result = new StringBuilder();

        List<String> stringSequence = toStringSequence(object);

        stringSequence.forEach(s -> result.append(s).append(separator));

        return result.toString();
    }


    //TODO testing!
    /**
     * converts a given String to an object.
     * Achieved by seperating the given string into a list of substrings that are passed to the
     * fromStringSequence operation, that converts them into an object
     * @param string the String to convert
     * @return the object that was constructed from the String
     */
    @Override
    public T convertFromString(String string) {
        if(string.charAt(0) == (getGoDeeper())){
            string = string.substring(1);
        }
        if(string.charAt(string.length() - 1) == (getGoUp())){
            string = string.substring(0, string.length() - 1);
        }

        return fromStringSequence(getStringSequence(string));
    }

    /**
     * a function that splits a String into its substrings
     * @param string the string to split
     * @return a List of strings, in order
     */
    private List<String> getStringSequence(final String string){
        List<String> sequence = new LinkedList<>();

        StringBuilder stringBuilder = new StringBuilder();
        int depth = 0;
        for(char c : string.toCharArray()){
            if(depth != 0 || c != getSeparator()){
                stringBuilder.append(c);
            }
            if(c == getGoDeeper()){
                depth++;
            }
            else if(c == getGoUp()){
                depth--;
            }
            else if(depth == 0 && c == getSeparator()){
                sequence.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            }
        }

        return sequence;
    }

    protected abstract List<String> toStringSequence(T object);
    protected abstract T fromStringSequence(List<String> strings);
}
