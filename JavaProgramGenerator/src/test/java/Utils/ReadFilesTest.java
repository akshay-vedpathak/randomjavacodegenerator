package Utils;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadFilesTest{

    List<String> topList;
    List<String> lowList;
    Map<String, String> grammarMap;
    Map<String,Integer> lowListElement;

    @Before
    public void setUp(){
       topList = new ArrayList<>();
       lowList = new ArrayList<>();
       grammarMap = new HashMap<>();
       lowListElement = new HashMap<>();
    }

    @Test
    public void readGrammarFileTest(){
        ReadFiles.readGrammarFile(topList,lowList,grammarMap,lowListElement, new ArrayList<>(),Utilities.parseConfigFile("resources\\config.xml"));
        System.out.println("TOPLIST");
        for(String top: topList){
            System.out.println(top);
        }
        System.out.println("LOWLIST");
        for(String low: lowList){
            System.out.println(low);
        }
        for(Map.Entry entry: grammarMap.entrySet()){
            System.out.println("Lefthandside: "+entry.getKey()+"   "+"Righthandside: "+entry.getValue());
        }
    }
}
