package Utils;

import java.util.List;
import java.util.Map;

public class MethodGenerator {

    public static String generateMethodsforInterface(String grammer, Map<String,String> grammerMap, Map<String,String> regexMap, Configuration config){
        StringBuilder methodDefinition = new StringBuilder();
        String[] split = grammer.split(" ");
        for(int i=0;i<split.length;i++){
            if(split[i].charAt(0) == '<'){
                if(grammerMap.containsKey(split[i])){
                    String value = grammerMap.get(split[i]);
                    String[] valueArray = value.split("[|]");
                    String modifier = Utilities.getRandomFromList(valueArray);
                    methodDefinition.append(modifier+" ");
                }else if(regexMap.containsKey(split[i])){
                    String regex = regexMap.get(split[i]);
                    String name = Utilities.getRandomString(regex,3,config.getMaxClassNameLength());
                    methodDefinition.append(name);
                }
            }else if(split[i].charAt(0)=='\''){
                methodDefinition.append(split[i].replaceAll("'",""));
            }
        }

        return methodDefinition.toString();
    }

    public static String generateMethodsforClass(String grammer, Map<String,String> grammerMap, Map<String,String> regexMap, List<String> lowList, String className, Configuration config){
        StringBuilder methodDeclaration = new StringBuilder();
        String[] split = grammer.split(" ");
        for(int i=0;i<split.length;i++){
            if(split[i].contains("access_modifier")){
                String value = grammerMap.get(split[i]);
                String[] valueArray = value.split("[|]");
                String modifier = Utilities.getRandomFromList(valueArray);
                methodDeclaration.append(modifier+" ");
            }else if(split[i].contains("method_name")){
                String regex = regexMap.get(split[i]);
                String name = Utilities.getRandomString(regex,3,config.getMaxMethodNameLength());
                methodDeclaration.append(name+" ");
            }else if(split[i].contains("<expression>")){

                methodDeclaration.append(" "+Generator.evaluateExpression(lowList.get(0),grammerMap,regexMap, config.getMaxRecurssionLevel()));

            }else if(split[i].contains("<for_loop>")){

                methodDeclaration.append(LoopGenerator.loopEvaluator(lowList,grammerMap.get("<for_loop>"),grammerMap,regexMap, config));

            }
            else if(split[i].charAt(0)=='\''){
                methodDeclaration.append(split[i].replaceAll("'","")+" ");
            }

        }
        methodDeclaration.append("\n");

        return methodDeclaration.toString();

    }

}
