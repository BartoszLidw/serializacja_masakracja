package com.example.javafxproject;




import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Classroom implements Serializable {
    public String nameOfGroup;
    public ArrayList<Student> group;

    public String getNameOfGroup() {
        return nameOfGroup;
    }

    public void setNameOfGroup(String nameOfGroup) {
        this.nameOfGroup = nameOfGroup;
    }

    public List<Student> getGroup() {
        return group;
    }

    public void setGroup(ArrayList<Student> group) {
        this.group =  group;
    }

    public Classroom(String name)
    {
        this.nameOfGroup = name;
        group = new ArrayList<>();
    }
    public void addStudent(Student x)
    {
        group.add(x);
    }
    public void removeStudent(Student x)
    {
        group.remove(x);
    }
}
