import java.util.*;

public class TodoApp {
    static ArrayList<String> todos = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Todo");
            System.out.println("2. View Todos");
            System.out.println("3. Delete Todo");
            System.out.println("4. Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter todo: ");
                String todo = sc.nextLine(); // BUG: skips input
                todos.add(todo);
                System.out.println("Todo added!");
            }

            else if (choice == 2) {
                System.out.println("Your Todos:");
                for (int i = 0; i <= todos.size(); i++) { // BUG: <= causes IndexOutOfBounds
                    System.out.println((i + 1) + ". " + todos.get(i));
                }
            }

            else if (choice == 3) {
                System.out.print("Enter todo number to delete: ");
                int index = sc.nextInt();
                todos.remove(index); // BUG: user enters 1-based, list uses 0-based
                System.out.println("Todo deleted!");
            }

            else if (choice == 4) {
                System.out.println("Goodbye!");
                break;
            }

            else {
                System.out.println("Invalid choice");
            }
        }

        sc.close();
    }
}
