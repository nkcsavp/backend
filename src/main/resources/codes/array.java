import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class Main{
    private static String ret = "$(ops)";
    public static void main(String[] args) {
        $(code)
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println(ret.replace("$(ops)",String.join(",",DataList.operations)));
            }
        });
    }
}

class DataList {
    static ArrayList<String> operations = new ArrayList<>();
    List<Integer> arr;
    public DataList() {
        arr = Arrays.asList($(sample));
    }
    public Integer get(int idx) {
        operations.add("get" + "(" + idx + ")");
        return arr.get(idx);
    }
    public void swap(int idx1, int idx2) {
        int temp = arr.get(idx1);
        arr.set(idx1, arr.get(idx2));
        arr.set(idx2, temp);
        operations.add("swap" + "(" + idx1 + "," + idx2 + ")");
    }
    public int size() {
        return arr.size();
    }
}