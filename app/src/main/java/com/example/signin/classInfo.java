package com.example.signin;

public class classInfo {
    private String name;
    private String classId;
    public classInfo(String name,String classId){
        this.name=name;
        this.classId=classId;
    }
    public String getName(){return name;}
    public String getClassId(){return classId;}
    public void setName(String name){this.name=name;}
    public void setClassId(String classId){this.classId=classId;}
}
