package com.example.signin;

public class memberClass {
    private String stu_id,stu_name;

    public memberClass(String stu_id,String stu_name)
    {
        this.stu_id=stu_id;
        this.stu_name=stu_name;
    }

    public String getStu_id(){return stu_id;}
    public String getStu_name(){return stu_name;}
    public void setStu_name(String stu_name){this.stu_name=stu_name;}
    public void setStu_id(String stu_id){this.stu_id=stu_id;}
}


