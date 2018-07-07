package TwoDMap;

import java.util.*;
import java.util.stream.Collectors;

public class StringMap2DImplList implements StringMap2D {

    Set<Entry> list;

    public StringMap2DImplList() {
        this.list = new HashSet<>();
    }

    @Override
    public String put(String row, String column, String value) {
        String f = get(row,column);
        list.removeIf(m -> m.getFirstKey().equals(row) && m.getSecondKey().equals(column));
        Entry e = new Entry(row,column,value);
        list.add(e);
        /*if(list.size() != 0) {
            for (Entry e : list) {
                if (e.getFirstKey().equals(row)) {
                    if (e.getSecondKey().equals(column)) {
                        String f = e.getValue();
                        list.remove(e);
                        Entry entry = new Entry(row, column, value);
                        list.add(entry);
                        return f;
                    }
                }
            }
        }
        Entry entry = new Entry(row, column, value);
        list.add(entry);*/
        return f;
    }

    @Override
    public String get(String row, String column) {
        try {
            return list.stream().filter(m -> m.getSecondKey().
                    equals(column) && m.getFirstKey().equals(row)).findFirst().get().getValue();
        }catch (NoSuchElementException e) {
            return null;
        }
        /*for(Entry e : list) {
            if(e.getFirstKey().equals(row)) {
                if(e.getSecondKey().equals(column)) {
                    return e.getValue();
                }
            }
        }
        return null; */
    }

    @Override
    public String remove(String row, String column) {
        /*Entry f = list.stream().filter(m -> m.getFirstKey().equals(row) && m.getSecondKey().equals(column)).findFirst().get();
        list.remove(f);
        return f.getValue();
        */
        String f = get(row,column);
        list.removeIf(m -> m.getFirstKey().equals(row) && m.getSecondKey().equals(column));

        return f;
        /*for(Entry e : list) {
            if(e.getFirstKey().equals(row)) {
                if(e.getSecondKey().equals(column)) {
                    String f = e.getValue();
                    list.remove(e);
                    return f;
                }
            }
        }
        return null; */
    }

    @Override
    public boolean contains(String row, String column) {
        return list.stream().anyMatch(m -> m.getFirstKey().equals(row) && m.getSecondKey().equals(column));
    }

    @Override
    public boolean contains(String value) {
        return list.stream().anyMatch(m -> m.getValue().equals(value));
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.size()==0;
    }

    @Override
    public Collection<String> values() {
        return list.stream().map(e -> e.getValue()).collect(Collectors.toList());
    }

    @Override
    public Iterator<Entry> iterator() {
        return list.iterator();
    }

    @Override
    public StringMap2D flipped() {
        StringMap2D m = new StringMap2DImplList();
        for(Entry e : list) {
            m.put(e.getSecondKey(), e.getFirstKey(), e.getValue());
        }
        return m;
    }

    public Collection<String> firstKey() {
        return list.stream().map(e -> e.getFirstKey()).collect(Collectors.toList());
    }
    public Collection<String> secondKey() {
        return list.stream().map(e -> e.getSecondKey()).collect(Collectors.toList());
    }

    public String toString() {
        /*String f = "Flipped map \n";
        for(Entry e : list) {
            f += "First Key: " + e.getFirstKey() + "\nSecond Key: " + e.getSecondKey() + "\nValue: " + e.getValue() + "\n";
        }
        return f;*/
        String f = list.stream().map(Entry::toString).collect(Collectors.joining(",","[","}"));
        return f;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if(!(o instanceof StringMap2DImplList)) return false;
        StringMap2DImplList x = (StringMap2DImplList) o;
        if(x == null) {
            if(x.list != null) {
                return false;
            } else if(!list.equals(x.list)) {
                return false;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((list == null) ? 0 : list.hashCode());
        return result;
    }

}
