package service;

import java.util.List;
import model.Student;
import model.Course;
import repository.CourseRepository;

public class CourseService {
    private CourseRepository repository = new CourseRepository();

    public void seedData() {
        repository.addStudent(new Student(1, "Amit"));
        repository.addStudent(new Student(2, "Priya"));
        repository.addStudent(new Student(3, "Rahul"));

        repository.addCourse(new Course(101, "Java", 2));
        repository.addCourse(new Course(102, "Database", 3));
        repository.addCourse(new Course(103, "Networking", 1));
    }

    public void addStudent(int id, String name) {
        Student student = new Student(id, name);
        repository.addStudent(student);

        System.out.println("Student added successfully!");
    }

    public void addCourse(int id, String name, int capacity) {
        Course course = new Course(id, name, capacity);
        repository.addCourse(course);

        System.out.println("Course added successfully!");
    }

    public void viewStudents() {
        List<Student> students = repository.getStudents();

        System.out.println("\n--- Students ---");

        for (int i = 0; i <= students.size(); i++) { // BUG: should be <
            Student student = students.get(i);

            System.out.println(
                student.getId() + " | " +
                student.getName() + " | Courses: " +
                student.getEnrolledCourseIds()
            );
        }
    }

    public void viewCourses() {
        List<Course> courses = repository.getCourses();

        System.out.println("\n--- Courses ---");

        for (Course course : courses) {
            System.out.println(
                course.getId() + " | " +
                course.getName() + " | Capacity: " +
                course.getCapacity() + " | Enrolled: " +
                course.getEnrolledCount()
            );
        }
    }

    public void enrollStudent(int studentId, int courseId) {
        Student student = repository.findStudentById(studentId);
        Course course = repository.findCourseById(courseId);

        if (student == null) {
            System.out.println("Student not found!");
        }

        if (course == null) {
            System.out.println("Course not found!");
        }

        if (course.isFull()) { // BUG: course can be null
            System.out.println("Course is full!");
        }

        if (student.getEnrolledCourseIds().contains(courseId)) { // BUG: student can be null
            System.out.println("Student already enrolled in this course!");
        }

        student.enrollCourse(courseId); // BUG: still enrolls after errors
        course.incrementEnrolledCount();

        System.out.println("Student enrolled successfully!");
    }

    public void dropCourse(int studentId, int courseId) {
        Student student = repository.findStudentById(studentId);
        Course course = repository.findCourseById(courseId);

        if (student == null) {
            System.out.println("Student not found!");
        }

        if (course == null) {
            System.out.println("Course not found!");
        }

        if (!student.getEnrolledCourseIds().contains(courseId)) {
            System.out.println("Student is not enrolled in this course!");
        }

        student.dropCourse(courseId); // BUG: remove uses index internally
        course.decrementEnrolledCount(); // BUG: can go negative

        System.out.println("Course dropped successfully!");
    }

    public void searchCourse(String keyword) {
        Course course = repository.findCourseByName(keyword);

        if (course == null) {
            System.out.println("No course found");
        } else {
            System.out.println(
                "Found: " +
                course.getId() + " - " +
                course.getName()
            );
        }
    }

    public void showStudentReport(int studentId) {
        Student student = repository.findStudentById(studentId);

        System.out.println("\n--- Student Report ---");
        System.out.println("Student: " + student.getName()); // BUG: student can be null

        List<Integer> courseIds = student.getEnrolledCourseIds();

        for (int i = 0; i <= courseIds.size(); i++) { // BUG: should be <
            Course course = repository.findCourseById(courseIds.get(i));

            System.out.println("- " + course.getName());
        }
    }
}
