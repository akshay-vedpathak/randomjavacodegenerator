package Utils;

import Dao.ClassMethodDao;

import java.util.*;

public class HierarchyMapper {

    //Classes and methods are stored as key value pairs, all classes can be accessed using keyset()

    public static void setHierarchyDetails(String className, String classParameter){
        if (className.equals(classParameter)){
            Map<String, ArrayList<String>> tempMap = new HashMap<>();
            ClassMethodDao.getClassMethod().put(className, new ArrayList<String>());
        }
        else
        {
            Map classParamsMap = ClassMethodDao.getClassMethod();
            ArrayList methodList = (ArrayList) classParamsMap.get(className);
            methodList.add(classParameter);
            ClassMethodDao.getClassMethod().put(className, methodList );
        }
    }
}
