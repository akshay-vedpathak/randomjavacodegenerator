package Utils;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GenerateAndCompileTest {

    List<String> code = new ArrayList<>();

    @Before
    public void setUp(){
        String source = "public class ABC{\n" +
                "public static void main(String args[]){\n" +
                "System.out.println(\"Hello world!\");\n" +
                "}\n" +
                "}";

        String interfaceSource = "public interface XYZ{\n" +
                "void printTime();\n" +
                "}";
        code.add(source);
        code.add(interfaceSource);
    }

    @Test
    public void testGenerateAndCompile(){
        GenerateAndCompile.generateAndCompileJavaFile(code);
    }
}
