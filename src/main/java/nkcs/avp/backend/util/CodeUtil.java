package nkcs.avp.backend.util;

import djudger.util.DockerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class CodeUtil {
    static {
        CodeUtil.codes = new HashMap<>();
        String[] fileList = new String[]{"codes/array.java","codes/tree.java","codes/graph.java","codes/array.py","codes/tree.py","codes/graph.py","codes/array.cpp","codes/tree.cpp","codes/graph.cpp"};
        String[] typeList = new String[]{"array_java","tree_java","graph_java","array_python","tree_python","graph_python","array_cpp","tree_cpp","graph_cpp"};
        InputStream inputStream = null;
        for(int i = 0; i < fileList.length; i++){
            inputStream = DockerAdapter.class.getClassLoader().getResourceAsStream(fileList[i]);
            CodeUtil.codes.put(typeList[i],new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n")));
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        CodeUtil.commands = new HashMap<>();
        CodeUtil.commands.put("java", Arrays.asList("cd $(directory)","javac $(code)","java Main"));
        CodeUtil.commands.put("python", Arrays.asList("cd $(directory)","python $(code)"));
        CodeUtil.commands.put("cpp", Arrays.asList("cd $(directory)","g++ $(code)","./a.out"));
    }
    public static Map<String,String> codes;
    public static Map<String, List<String>> commands;
}
