import Dao.ClassMethodDao;
import Utils.*;

import java.util.*;

public class ProgramGenerator {



    static Stack<String> rightStack ;

    static StringBuffer result ;
    static String className;

    static Queue<String> leftQueue;

    static Map<String,String> regexMap;

    static Utilities utilities;
    static Configuration config;

    static List<String> topList;
    static  List<String> lowList;
    static Map<String,Integer> lowListElement;
    static  Map<String,String> grammarMap;
    static  Queue<String> topQueue;
    static ArrayList<String> resultList;
    static List<String> inheritenceLevel;


   public static void initializeAllDataHolders() {
       //stack for processing right hand of expression
       rightStack = new Stack<>();
       //result after processing topqueue
       result = new StringBuffer();
       //queue for processing left hand of expression
       leftQueue =  new LinkedList<>();
       //regex for the variable, class and other names
       regexMap = new HashMap<>();
       utilities = new Utilities();
       //class and intergace
       topList = new ArrayList<>();
       //currently expression
       lowList = new ArrayList<>();
       //all other grammar which are not part of top and low list
       grammarMap = new HashMap<>();
       //grammar selected from top list
       topQueue = new LinkedList<>();
       //count of lowlist elements in lowlist -e.g <expression>,3
       lowListElement = new HashMap<>();
        //final result list
       resultList = new ArrayList<>();

       inheritenceLevel = new ArrayList<>();

    }



    public static void main(String[] args) {
        config = Utilities.parseConfigFile("resources\\config.xml");
        utilities = new Utilities();

        initializeAllDataHolders();

        Configuration config = Utilities.parseConfigFile("src/main/resources/config.xml");

        //make list top-level mid-level and low-level
        ReadFiles.readGrammarFile(topList,lowList,grammarMap,lowListElement,inheritenceLevel,config);

        //no changes required here
        ReadFiles.readRegexFile(regexMap);


        queueProcessing(config);

        GenerateAndCompile.generateAndCompileJavaFile(resultList);
        System.out.println(result.toString());
    }


    public static void queueProcessing(Configuration config) {
        /*
            1. read top queue one element at a time
                a. if not in lowlist then remove
                b. else peek and iterate over it
            2. check corresponding element in map
                a. check if it has pipe - if yes split on pipe and generate random number
                b. if not then has regex for it then generate random string name
                c. if has '' then just append name directly
            3. if lowlevel
                a. check count
                    if count less than set count then iterate - solve using left queue and right stack
                    if count equals set count then remove and iterate using left queue and right stack

            4. for left queue - check for element in map and solve
            5. same for right queue
         */
        int classCount = config.getMaxClasses();
        int interfaceCount=config.getMaxInterfaces();
        int inheritenceCount=2;
        String interfaceName = null;



        while( inheritenceCount!=0 || classCount!=0 || interfaceCount!=0)  {
            if(classCount>0 || interfaceCount>0) {
                Generator.generateTopLevelQueue(topList, topQueue, classCount, interfaceCount);
            }else{
                Generator.populateQueue(topQueue,inheritenceLevel.get(0));
                inheritenceCount--;
            }

            while (!topQueue.isEmpty()) {
                String element = topQueue.peek();
                if(element.contains("'interface'") && !element.contains("<") && !element.contains("implements") && !topQueue.contains("implements"))
                    interfaceCount--;
                if(element.contains("'class'") && !element.contains("<") && !element.contains("implements") && !topQueue.contains("'implements'"))
                    classCount--;
                if (lowListElement.containsKey(element)) {
                    // check count and iterate
                    int lowElementCount = lowListElement.get(element);
                    while (lowElementCount != 0) {
                        String lowContent = topQueue.peek();

                        if (lowContent.equals("<expression>")) {
                             String resultFromExpression = Generator.evaluateExpression(lowList.get(0),grammarMap,regexMap, config.getMaxRecurssionLevel());
                             result.append(" " + resultFromExpression);
                          //  System.out.println("Result from expression-"+resultFromExpression);
                           // result.append(" <expression>");
                        }else if(lowContent.equals("<abstract_method>")){
                            String abstractMethod =  MethodGenerator.generateMethodsforInterface(lowList.get(2),grammarMap,regexMap,config);

                            HierarchyMapper.setHierarchyDetails(className, abstractMethod);
                            result.append(" "+ abstractMethod);
                        }else if(lowContent.equals("<class_method>")){
                            String classMethod = MethodGenerator.generateMethodsforClass(lowList.get(1),grammarMap,regexMap,lowList, className,config);
                            result.append(" "+ classMethod);
                        }


                        lowElementCount--;
                    }
                    topQueue.remove();


                } else {
                    // need to append to result
                    element = topQueue.remove();

                    if(element.equals("<previous_interface_name>")){
                        //select from previous interface
                        interfaceName = getInterfaceName();
                        result.append(" "+interfaceName);

                    }
                    if(element.equals("<interface_methods>")){
                        //implement interface methods here
                        if(interfaceName!=null){
                            List<String> interfaceMethods = getInterfaceMethods(interfaceName);

                            for(String interfaceMethod: interfaceMethods){
                                String methodDef = interfaceMethod.substring(0,interfaceMethod.indexOf(";"));
                                result.append(" "+methodDef+"\n"+"{");
                                result.append(" "+ Generator.evaluateExpression(lowList.get(0),grammarMap,regexMap, config.getMaxRecurssionLevel()));
                                if(methodDef.contains("int") || methodDef.contains("float")){
                                    result.append("return 1;"+"\n");

                                }
                                result.append("}");
                            }
                        }
                    }

                    if (grammarMap.containsKey(element)) {
                        String grammar = grammarMap.get(element);
                        if (grammar.contains("<") && grammar.contains(">")) {



                        } else {
                            if (grammar.contains("|")) {
                                String[] splittedGrammar = grammar.split("\\|");
                                String appender = Utilities.getRandomFromList(splittedGrammar);
                                result.append(appender);

                            }

                        }


                    } else {
                        if (element.contains("<") && element.contains(">") && regexMap.containsKey(element)) {
                            className = Utilities.getRandomString(regexMap.get(element), 0, config.getMaxClassNameLength());
                            HierarchyMapper.setHierarchyDetails(className,className);
                            result.append(" " + className);

                        } else if (element.contains("'")) {
                            result.append(" " + element.substring(1, element.length() - 1));
                        }
                    }
                }
            }

            resultList.add(result.toString());

            System.out.println("String after queue processing-" + result);
            result.delete(0,result.length());

        }

        getInterfaceName();


    }

    private static String getInterfaceName() {
        for(String className : ClassMethodDao.getClassMethod().keySet()){
             System.out.println("Class Name-" +className);

             for (String methods : ClassMethodDao.getClassMethod().get(className)){
                 return className;
             }

        }
        return "";
    }

    private static List<String> getInterfaceMethods(String interfaceName) {
       List<String> interfaceMethods = ClassMethodDao.getClassMethod().get(interfaceName);
        return interfaceMethods;
    }

}
