package nkcs.avp.backend.util;

import djudger.util.DockerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class CodeUtil {
    static {
        InputStream inputStream = DockerAdapter.class.getClassLoader().getResourceAsStream("codes/array.java");
        array = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputStream = DockerAdapter.class.getClassLoader().getResourceAsStream("codes/tree.java");
        tree = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining("\n"));
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String array;
    public static String tree;
}
