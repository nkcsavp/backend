import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class Main{
    public static void main(String[] args) {
        $(code)
        System.out.print(String.join(",",DataList.operations));
    }
}

class DataList {
    static ArrayList<String> operations = new ArrayList<>();
    List<Integer> arr;
    public DataList() {
        arr = Arrays.asList($(sample));
    }
    public Integer get(Integer idx) {
        operations.add("get" + "(" + idx + ")");
        return arr.get(idx);
    }
    public void swap(Integer i, Integer j) {
        int temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
        operations.add("swap" + "(" + i + "," + j + ")");
    }
    public Integer size() {
        return arr.size();
    }
}