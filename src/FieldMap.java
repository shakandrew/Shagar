import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by koshachok on 27.05.2017.
 */
public class FieldMap {
    private TreeMap<Integer, TreeSet<Integer>> data;

    public FieldMap() {
        data = new TreeMap<>();
    }

    public void setData(int first, int second) {
        TreeSet<Integer> temp;
        if (!data.containsKey(first)) {
            temp = new TreeSet<>();
            temp.add(second);
            data.put(first, temp);
        } else {
            temp = data.get(first);
            temp.add(second);
        }
    }

    public ArrayList<Pair<Integer, Integer>> getDataFromRange(int x1, int x2, int y1, int y2) {
        ArrayList<Pair<Integer, Integer>> result = new ArrayList<>();
        int x, y;
        TreeSet<Integer> temp;
        if (data.ceilingKey(x1) == null) return result;
        x = data.ceilingKey(x1);
        if (x > x2) return result;
        do {
            temp = data.ceilingEntry(x).getValue();
            if (temp.ceiling(y1) == null) {
                if (data.higherKey(x) == null) break;
                x = data.higherKey(x);
                continue;
            }

            y = temp.ceiling(y1);
            if (y < y2) {
                do {
                    result.add(new Pair<>(x, y));
                    if (temp.higher(y) == null) break;
                    y = temp.higher(y);
                } while (y <= y2);
            }
            if (data.higherKey(x) == null) break;
            x = data.higherKey(x);
        } while (x <= x2);

        return result;
    }

    public boolean checkData(int x, int y) {
        if (data.containsKey(x))
            return data.get(x).contains(y);
        return false;
    }

    public void delData(int first, int second) {
        if (!checkData(first, second)) return;
        TreeSet<Integer> value = data.get(first);
        value.remove(second);
        if (value.size() == 0) data.remove(first);
    }

    public Pair<Integer, Integer> minDist(int x, int y) {
        Pair<Integer, Integer> result = new Pair<>(0, 0);
        int x1,y1;
        TreeSet<Integer> temp;
        if (data.ceilingKey(x)==null)
            if (data.floorKey(x)==null)
                return result;
            else
                x1 = data.floorKey(x);
        else
            x1 = data.ceilingKey(x);
        temp = data.get(x1);
        if (temp.ceiling(y)==null)
            if (temp.floor(y)==null)
                return result;
            else
                y1 = temp.floor(y);
        else
            y1 = temp.ceiling(y);
        result.setFirst(x1);
        result.setSecond(y1);
        return result;

    }

}
