package edu.uncc.evaluation04.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Grade implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    String semester;
    String course;
    String letterGrade;
    double hours;
    //String courseName;

    public Grade(){

    }

    public Grade(String semester, String course, String letterGrade, double hours) {
        this.semester = semester;
        this.course = course;
        this.letterGrade = letterGrade;
        this.hours = hours;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

//    @Override
//    public String toString() {
//        return "Grade{" +
//                "id=" + id +
//                ", semester='" + semester + '\'' +
//                ", course='" + course + '\'' +
//                ", letterGrade='" + letterGrade + '\'' +
//                ", hours=" + hours +
//                '}';
//    }

}
