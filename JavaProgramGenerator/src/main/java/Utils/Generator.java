package Utils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {

    static int limitrecur;
    private static String non_recursive = "digit";

    public static void generateTopLevelQueue(List<String> topList, Queue<String> topQueue,int classCount,int interfaceCount){
        //populate top queue from top list
        //class and
        //random 0 -
        String grammar = "";
        if(topList.size() > 0) {
            if(classCount>0 && interfaceCount>0)
                grammar = getRandomTopElement(topList);
            else{
                if(classCount<=0)
                    grammar = topList.get(1);
                if (interfaceCount<=0)
                    grammar = topList.get(0);
            }
        }
        populateQueue(topQueue, grammar);
    }

    public static void populateQueue(Queue<String> topQueue, String grammar) {
        String splittedArray[]= {};
        splittedArray = getSplittedArray(grammar);
        for (String splitted : splittedArray) {
            topQueue.add(splitted);
        }
    }

    private static String getRandomTopElement(List<String> topList) {
        int randomNum = ThreadLocalRandom.current().nextInt(0, topList.size());
        return topList.get(randomNum);
    }

    public static String[] getSplittedArray(String grammar) {
        String[] splitted = grammar.split("\\s+");
        return splitted;
    }


    public static String evaluateExpression(String expression, Map<String,String> grammarMap, Map<String,String> regexMap, int expLimit){
        StringBuffer result = new StringBuffer();
        limitrecur = 0;
        //evaluate left hand and right hand side of expression
        Queue<String> leftQueue = new LinkedList<>();
        Stack<String> rightStack = new Stack<>();
        String[] splittedExpression= expression.split(" = ");
        limitrecur++;
        String lhs[] = splittedExpression[0].split("\\s+");
        String rhs[] = splittedExpression[1].split("\\s+");

        for (String lhString : lhs) {
            leftQueue.add(lhString);
        }
        for (String rhString : rhs) {
            rightStack.push(rhString);
        }

        processLeftQueue(leftQueue, result, grammarMap, regexMap);
        processRightStack(rightStack, result, grammarMap, regexMap, expLimit);
        return result.toString();

    }

    private static void processRightStack(Stack<String> rightStack, StringBuffer result, Map<String,String> grammarMap, Map<String,String> regexMap, int expLimit) {
        while (!rightStack.isEmpty()){
            String stackElement = rightStack.pop();

            if (grammarMap.containsKey(stackElement)) {
                String grammar = grammarMap.get(stackElement);
                if (grammar.contains("|")) {
                    String[] splittedGrammar = grammar.split(" \\| ");
                    if(limitrecur < expLimit)
                        stackElement = Utilities.getRandomFromList(splittedGrammar);
                    else
                        stackElement = splittedGrammar[1];

                }
                if (stackElement.contains("<") && stackElement.contains(">")) {
                    String splittedArray[] = getSplittedArray(stackElement);
                    for (String splitted : splittedArray){
                        rightStack.push(splitted);
                    }
                    if (!stackElement.contains(non_recursive))
                        limitrecur++;
                }
                else {
                    result.append(" " + stackElement);
                }
            } else {
                if (stackElement.contains("<") && stackElement.contains(">") && regexMap.containsKey(stackElement)) {
                    result.append(" " + Utilities.getRandomString(regexMap.get(stackElement), 0, 3));

                } else if (stackElement.contains("'")) {
                    result.append(" " + stackElement.substring(1, stackElement.length() - 1));
                }
            }
        }
        result.append(";");
        result.append(System.getProperty("line.separator"));
    }

    private static void processLeftQueue(Queue<String> leftQueue, StringBuffer result, Map<String,String> grammarMap, Map<String,String> regexMap) {
        for (String lElement : leftQueue){
            if (grammarMap.containsKey(lElement)) {
                String grammar = grammarMap.get(lElement);
                if (grammar.contains("|")) {
                    String[] splittedGrammar = grammar.split(" \\| ");
                    String appender = Utilities.getRandomFromList(splittedGrammar);
                    result.append(" " + appender);
                }
            } else {
                if (lElement.contains("<") && lElement.contains(">") && regexMap.containsKey(lElement)) {
                    result.append(" " + Utilities.getRandomString(regexMap.get(lElement), 0, 5));

                } else if (lElement.contains("'")) {
                    result.append(" " + lElement.substring(1, lElement.length() - 1));
                }
            }
        }
        result.append(" = ");
    }
}
