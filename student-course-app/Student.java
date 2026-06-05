package model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private String name;
    private List<Integer> enrolledCourseIds;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
        this.enrolledCourseIds = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getEnrolledCourseIds() {
        return enrolledCourseIds;
    }

    public void enrollCourse(int courseId) {
        enrolledCourseIds.add(courseId);
    }

    public void dropCourse(int courseId) {
        enrolledCourseIds.remove(courseId); // BUG: removes by index, not value
    }
}
