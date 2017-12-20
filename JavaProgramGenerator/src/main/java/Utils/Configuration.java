package Utils;

import java.util.List;

public class Configuration {
    private  int maxLinesOfCode, maxClasses, maxMethodCalls, maxMethodsInClass, maxInterfaces;
    private  int maxMethodsInInterface, maxInterfacesToImplement, maxIntValue, maxStringLength;
    private  int maxVariableNameLength, maxClassNameLength, maxMethodNameLength;
    private int maxRecurssionLevel;
    private  List<String> allowedTypes;

    public int getMaxMethodNameLength() {
        return maxMethodNameLength;
    }

    public void setMaxMethodNameLength(int maxMethodNameLength) {
        this.maxMethodNameLength = maxMethodNameLength;
    }

    public int getMaxClassNameLength() {
        return maxClassNameLength;
    }

    public void setMaxClassNameLength(int maxClassNameLength) {
        this.maxClassNameLength = maxClassNameLength;
    }

    private List<String> accessModifiers;

    private Configuration(){};

    private static Configuration configuration = null;

    public static Configuration getInstance(){
        if(configuration==null){
            configuration = new Configuration();
        }
        return configuration;
    }

    public int getMaxLinesOfCode() {
        return maxLinesOfCode;
    }

    public void setMaxLinesOfCode(int maxLinesOfCode) {
        this.maxLinesOfCode = maxLinesOfCode;
    }

    public int getMaxClasses() {
        return maxClasses;
    }

    public void setMaxClasses(int maxClasses) {
        this.maxClasses = maxClasses;
    }

    public int getMaxMethodCalls() {
        return maxMethodCalls;
    }

    public void setMaxMethodCalls(int maxMethodCalls) {
        this.maxMethodCalls = maxMethodCalls;
    }

    public int getMaxMethodsInClass() {
        return maxMethodsInClass;
    }

    public void setMaxMethodsInClass(int maxMethodsInClass) {
        this.maxMethodsInClass = maxMethodsInClass;
    }

    public int getMaxInterfaces() {
        return maxInterfaces;
    }

    public void setMaxInterfaces(int maxInterfaces) {
        this.maxInterfaces = maxInterfaces;
    }

    public int getMaxMethodsInInterface() {
        return maxMethodsInInterface;
    }

    public void setMaxMethodsInInterface(int maxMethodsInInterface) {
        this.maxMethodsInInterface = maxMethodsInInterface;
    }

    public int getMaxInterfacesToImplement() {
        return maxInterfacesToImplement;
    }

    public void setMaxInterfacesToImplement(int maxInterfacesToImplement) {
        this.maxInterfacesToImplement = maxInterfacesToImplement;
    }

    public int getMaxIntValue() {
        return maxIntValue;
    }

    public void setMaxIntValue(int maxIntValue) {
        this.maxIntValue = maxIntValue;
    }

    public int getMaxStringLength() {
        return maxStringLength;
    }

    public void setMaxStringLength(int maxStringLength) {
        this.maxStringLength = maxStringLength;
    }

    public int getMaxVariableNameLength() {
        return maxVariableNameLength;
    }

    public void setMaxVariableNameLength(int maxVariableNameLength) {
        this.maxVariableNameLength = maxVariableNameLength;
    }

    public List<String> getAllowedTypes() {
        return allowedTypes;
    }

    public void setAllowedTypes(List<String> allowedTypes) {
        this.allowedTypes = allowedTypes;
    }

    public List<String> getAccessModifiers() {
        return accessModifiers;
    }

    public void setAccessModifiers(List<String> accessModifiers) {
        this.accessModifiers = accessModifiers;
    }

    public int getMaxRecurssionLevel() {
        return maxRecurssionLevel;
    }

    public void setMaxRecurssionLevel(int maxRecurssion) {
        this.maxRecurssionLevel = maxRecurssion;
    }

}
