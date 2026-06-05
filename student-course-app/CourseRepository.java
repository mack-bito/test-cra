package repository;

import java.util.ArrayList;
import java.util.List;
import model.Student;
import model.Course;

public class CourseRepository {
    private List<Student> students = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }

        return null;
    }

    public Course findCourseById(int id) {
        for (Course course : courses) {
            if (course.getId() == id) {
                return course;
            }
        }

        return null;
    }

    public Course findCourseByName(String name) {
        for (Course course : courses) {
            if (course.getName() == name) { // BUG: should use equalsIgnoreCase
                return course;
            }
        }

        return null;
    }
}
