package Utils;

import javax.tools.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GenerateAndCompile {

    public static void generateAndCompileJavaFile(List<String> javaCode){
        for(String source : javaCode) {
            File sourceFile = null;
            String classSearch = " class";
            String interfaceSearch = "public interface";
            String str = "";
            try {
                if(source.contains(classSearch) && source.contains("implements")){
                    str= source.substring(source.indexOf(classSearch)+classSearch.length(),source.indexOf("implements"));
                } else if(source.contains(classSearch)) {
                    str= source.substring(source.indexOf(classSearch)+classSearch.length(),source.indexOf("{"));
                }else if(source.contains(interfaceSearch)) {
                    str= source.substring(source.indexOf(interfaceSearch)+interfaceSearch.length(),source.indexOf("{"));
                }
                File sourceDir = new File("./generatedCode/");
                if(!sourceDir.exists()){
                    sourceDir.mkdir();
                }
                sourceFile = new File(sourceDir+"/"+str.trim()+".java");
                String[] sourceCode = source.split("\n");
                // write the source code into the source file
                FileWriter writer = null;
                writer = new FileWriter(sourceFile);
                for(String sc:sourceCode){
                    writer.append(sc);
                }
                writer.close();
                // compile the source file


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
