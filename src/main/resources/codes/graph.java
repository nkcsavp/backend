import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        $(code)
        System.out.print(String.join(":",Graph.operations));
    }
}

class Graph {
    static ArrayList<String> operations = new ArrayList<>();
    public Integer[][] relation;
    public List<Integer> data;

    public Graph() {
        data = Arrays.asList($(sample));
        Integer[] temp = new Integer[]{$(relation)};
        relation = new Integer[data.size()][data.size()];
        for (int i = 0; i < data.size(); i++) {
            System.arraycopy(temp, i * data.size(), relation[i], 0, data.size());
        }
    }

    public void emphasize(Integer i, Integer j) {
        if(i >= data.size() || j >= data.size()){
            throw new IndexOutOfBoundsException();
        }
        operations.add("link(" + i + "," + j + ")");
    }

    public void emphasize(Integer i) {
        if(i >= data.size()){
            throw new IndexOutOfBoundsException();
        }
        operations.add("emp(" + i + ")");
    }

    public void deEmphasize(Integer i, Integer j) {
        if(i >= data.size() || j >= data.size()){
            throw new IndexOutOfBoundsException();
        }
        operations.add("unlink(" + i + "," + j + ")");
    }

    public void deEmphasize(Integer i) {
        if(i >= data.size()){
            throw new IndexOutOfBoundsException();
        }
        operations.add("unemp(" + i + ")");
    }
}