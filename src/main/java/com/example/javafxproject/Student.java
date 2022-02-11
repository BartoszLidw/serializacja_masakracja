package com.example.javafxproject;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {
    public String name, surname;
    ArrayList<ArrayList<Double>> marks;
    ArrayList<Classroom> classrooms;
    public Student(String name, String surname)
    {
        this.name = name;
        this.surname = surname;
        marks = new ArrayList<>();
        classrooms = new ArrayList<>();
    }
    public void addClassroom(Classroom x)
    {
        marks.add(new ArrayList<>());
        classrooms.add(x);
        x.addStudent(this);
    }
    public void removeClassroom(Classroom x)
    {
        int index;
        if(classrooms.contains(x))
        {
          index = classrooms.indexOf(x);
          marks.remove(index);
          classrooms.remove(index);
          x.removeStudent(this);
        }

    }
    public double getPoints(int index)
    {
        if(  marks.get(index).size() == 0 )
        {
            return 0;
        }
        double points = 0;
        for(int i = 0; i < marks.get(index).size(); i++)
        {
            points += marks.get(index).get(i);
        }
        return points/marks.get(index).size();
    }
    public double getPoints(Classroom index)
    {
        if (  marks.get(classrooms.indexOf(index)).size() == 0)
        {
            return 0;
        }
        double points = 0;
        for(int i = 0; i < marks.get(classrooms.indexOf(index)).size(); i++)
        {
            points += marks.get(classrooms.indexOf(index)).get(i);
        }
        return points/marks.get(classrooms.indexOf(index)).size();
    }
    public void addMark(Classroom index, Double mark)
    {

        marks.get(classrooms.indexOf(index)).add(mark);
    }
    public void addMark(int index, Double mark)
    {
        marks.get(index).add(mark);
    }
}
