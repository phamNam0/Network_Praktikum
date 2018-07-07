package TwoDMap;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/** 
 * A two-dimensional Map of Strings.
 * @author Reinhard Schiedermeier, rs@cs.hm.edu
 * @author Axel Boettcher, ab@cs.hm.edu
 */
public interface StringMap2D extends Iterable<StringMap2D.Entry>
{
    /** An entry in the Map, consists of two keys and a value. */
    final class Entry
    {
        /** First key. */
        private final String key1;

        /** Second key. */
        private final String key2;

        /** Value. */
        private final String value;

        /** Ctor for a new Triple.
         * @param key1 First key.
         * @param key2 Second key.
         * @param value Value.
         */
        public Entry(final String key1, final String key2, final String value) {
            this.key1 = key1;
            this.key2 = key2;
            this.value = value;
        }

        /**
         * Getter for first key.
         * @return first key.
         */
        String getFirstKey()
        {
            return key1;
        }

        /**
         * Getter for second key.
         * @return second key.
         */
        String getSecondKey()
        {
            return key2;
        }

        /**
         * Getter for the value.
         * @return the value.
         */
        String getValue() {
            return value;
        }

        @Override 
        public boolean equals(final Object anything) {
            if (anything == null) {
                return false;
            }
            if (getClass() != anything.getClass()) {
                return false;
            }
            final Entry that = (Entry)anything;
            return Objects.equals(getFirstKey(), that.getFirstKey())
                   && Objects.equals(getSecondKey(), that.getSecondKey())
                   && Objects.equals(getValue(), that.getValue());
        }

        // CHECKSTYLE- Magic Number
        @Override public int hashCode() {
            int hash = 7;
            hash = 17 * hash + Objects.hashCode(getFirstKey());
            hash = 17 * hash + Objects.hashCode(getSecondKey());
            hash = 17 * hash + Objects.hashCode(getValue());
            return hash;
        }
        // CHECKSTYLE+ Magic Number

        @Override public String toString() {
            return String.format("(%s, %s, %s)", getFirstKey(), getSecondKey(), getValue());
        }

    }

    /** 
     * Inserts the String value at position (row, column).
     * Any previous value at that position is replaced.
     * @param row "row" key.
     * @param column "column" key.
     * @param value Value to insert.
     * @return Old value at position (row, column) or null,
     * if nothing was stored there yet.
     * Be careful, null can also be the old value!
     */
    String put(String row, String column, String value);

    /** 
     * Returns the latest value stored at position (row, column).
     * @param row "row" key.
     * @param column "column" key.
     * @return value value at position (row, column) or null,
     * if nothing stored there yet.
     * Be careful: null can also be a value!
     */
    String get(String row, String column);

    /** 
     * Deletes the value at position (row, column).
     * @param row "row" key.
     * @param column "column" key.
     * @return value deleted value at (row, column) or null,
     * if nothing was stored there yet.
     * Be careful, null can also be the old value!
     */
    String remove(String row, String column);

    /** 
     * Indicates if at position (row, column) a value is stored.
     * @param row "row" key.
     * @param column "column" key.
     * @return true, if a value is stored at the oosition or false if not.
     */
    boolean contains(String row, String column);

    /** 
     * Indicates if a value is stored anywhere.
     * @param value Value.
     * @return true, if the value is present in the map, otherwise false.
     */
    boolean contains(String value);

    /** 
     * Clears the map. Does not contain any values afterwards.
     */
    void clear();

    /** 
     * Returns the number of values stored in the map.
     * @return (non-negaitve) number of values.
     */
    int size();

    /** 
     * Gives information if the map is empty.
     * @return true if this collection contains no elements, else false.
     */
    boolean isEmpty();

    /** 
     * Returns a Collection of all values.
     * @return Collection of all values.
     * The collection contains exactly as many elements as size indicates.
     */
    Collection<String> values();

    /** 
     * Returns an Iterator which iterates over all entries of the map.
     * If the map is modified while it is iterated, its behavior is undefined.
     * @return Iterator over all entries.
     * Jeder Eintrag wird als String-Tripel geliefert mit den drei Elementen
     * row, column, value.
     */
    @Override Iterator<Entry> iterator();

    /**
     * Returns a map that is mirrored at the diagonal.
     * I.E. row keys become column keys and column keys become row keys.
     * @return new maps with interchanged keys.
     */
    StringMap2D flipped();
}
