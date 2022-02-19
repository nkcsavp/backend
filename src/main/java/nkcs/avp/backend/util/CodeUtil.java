package nkcs.avp.backend.util;

import djudger.util.DockerAdapter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class CodeUtil {
    static {
        InputStream inputStream = DockerAdapter.class.getClassLoader().getResourceAsStream("codes/array.java");
        array = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
        inputStream = DockerAdapter.class.getClassLoader().getResourceAsStream("codes/tree.java");
        tree = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
    }
    public static String array;
    public static String tree;
}
