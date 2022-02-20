import java.util.ArrayList;
import java.util.Arrays;

public class Main{
    private static String ret = "$(ops)";
    public static void main(String[] args) {
        $(code)
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println(ret.replace("$(ops)",String.join(",",BinaryTree.operations)));
            }
        });
    }
}

class BinaryTree {
    static ArrayList<String> operations = new ArrayList<>();
    private BinaryTreeNode root;

    {
        this.root = this.initTreeByData();
    }

    public BinaryTree() {}

    private BinaryTreeNode initTreeByData(){
        ArrayList<BinaryTreeNode> nodeList = new ArrayList<>();
        for (Integer treeDatum : Arrays.asList($(sample))) {
            if(treeDatum <= 0){
                nodeList.add(null);
                continue;
            }
            nodeList.add(new BinaryTreeNode(treeDatum));
        }
        for (int i = 0; i < nodeList.size(); i++) {
            if(nodeList.get(i) != null){
                nodeList.get(i).position = i;
                if(2 * i + 1 < nodeList.size() && nodeList.get(2 * i + 1) != null) {
                    nodeList.get(i).left = nodeList.get(2 * i + 1);
                }
                if(2 * i + 2 < nodeList.size() && nodeList.get(2 * i + 2) != null) {
                    nodeList.get(i).right = nodeList.get(2 * i + 2);
                }
            }
        }
        return nodeList.size() == 0 ? null : nodeList.get(0);
    }
    public BinaryTreeNode getRoot() {
        return root;
    }
    public void swap(BinaryTreeNode node1, BinaryTreeNode node2){
        Integer temp = node1.value;
        node1.value = node2.value;
        node2.value = temp;
        operations.add("swap("+node1.position+","+node2.position+")");
    }
}

class BinaryTreeNode {
    Integer position = null;
    Integer value = null;
    BinaryTreeNode left = null;
    BinaryTreeNode right = null;
    BinaryTreeNode father = null;
    boolean isInit = true;
    public BinaryTreeNode(Integer value, BinaryTreeNode left, BinaryTreeNode right) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.position = 1;
    }
    public BinaryTreeNode(Integer value) {
        this.value = value;
        this.position = 1;
    }
    public Integer getValue() {
        BinaryTree.operations.add("get(" + this.position + ")");
        return value;
    }
    public void insertLeft(Integer value){
        this.left = new BinaryTreeNode(value);
        this.left.father = this;
        this.left.position = this.position * 2 + 1;
        BinaryTree.operations.add("insert(" + this.left.position + "," + value + ")");
    }
    public void insertRight(Integer value){
        this.right = new BinaryTreeNode(value);
        this.right.father = this;
        this.right.position = this.position * 2 + 2;
        BinaryTree.operations.add("insert(" + this.right.position + "," + value + ")");
    }
    public void removeLeft(){
        Integer removePosition = this.left.position;
        this.left = null;
        BinaryTree.operations.add("remove("+removePosition+")");
    }
    public void removeRight(){
        Integer removePosition = this.right.position;
        this.right = null;
        BinaryTree.operations.add("remove("+removePosition+")");
    }
}
