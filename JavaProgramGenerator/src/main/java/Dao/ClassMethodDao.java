package Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClassMethodDao {

    static Map<String, ArrayList<String>> ClassMethod = new HashMap<>();

    public static Map<String, ArrayList<String>> getClassMethod() {
        return ClassMethod;
    }

    public static void setClassMethod(Map<String, ArrayList<String>> classMethod) {
        ClassMethod = classMethod;
    }
}
