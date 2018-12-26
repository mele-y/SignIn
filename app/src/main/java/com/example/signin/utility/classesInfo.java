package com.example.signin.utility;

import com.example.signin.classInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class classesInfo {
    private static List<classInfo> classes = new ArrayList<>();

    public static List<classInfo> getClasses() {
        return classes;
    }

    public static void setClasses(List<classInfo> classes) {
        classesInfo.classes = classes;
    }

    public static List<classInfo> searchClass(String query){
        List<classInfo> cls =new ArrayList<>();
        String id, name;
        for(int i=0;i<classes.size();++i){
            name = classes.get(i).getName();
            id = classes.get(i).getClassId();
            if(name.equals(query) || id.equals(query)){
                cls.add(new classInfo(name, id));
            }
        }
        return cls;
    }
}
