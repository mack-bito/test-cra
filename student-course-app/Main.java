import java.util.Scanner;
import service.CourseService;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CourseService courseService = new CourseService();

        courseService.seedData();

        while (true) {
            System.out.println("\n===== Student Course App =====");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. View Students");
            System.out.println("4. View Courses");
            System.out.println("5. Enroll Student");
            System.out.println("6. Drop Course");
            System.out.println("7. Search Course");
            System.out.println("8. Show Student Report");
            System.out.println("9. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter student id: ");
                int id = sc.nextInt();

                System.out.print("Enter student name: ");
                String name = sc.nextLine(); // BUG: skips input

                courseService.addStudent(id, name);

            } else if (choice == 2) {
                System.out.print("Enter course id: ");
                int id = sc.nextInt();

                System.out.print("Enter course name: ");
                String name = sc.nextLine(); // BUG: skips input

                System.out.print("Enter capacity: ");
                int capacity = sc.nextInt();

                courseService.addCourse(id, name, capacity);

            } else if (choice == 3) {
                courseService.viewStudents();

            } else if (choice == 4) {
                courseService.viewCourses();

            } else if (choice == 5) {
                System.out.print("Enter student id: ");
                int studentId = sc.nextInt();

                System.out.print("Enter course id: ");
                int courseId = sc.nextInt();

                courseService.enrollStudent(studentId, courseId);

            } else if (choice == 6) {
                System.out.print("Enter student id: ");
                int studentId = sc.nextInt();

                System.out.print("Enter course id: ");
                int courseId = sc.nextInt();

                courseService.dropCourse(studentId, courseId);

            } else if (choice == 7) {
                System.out.print("Enter course name: ");
                String keyword = sc.nextLine(); // BUG: skips input

                courseService.searchCourse(keyword);

            } else if (choice == 8) {
                System.out.print("Enter student id: ");
                int studentId = sc.nextInt();

                courseService.showStudentReport(studentId);

            } else if (choice == 9) {
                System.out.println("Exiting...");
                break;

            } else {
                System.out.println("Invalid option");
            }
        }

        sc.close();
    }
}
