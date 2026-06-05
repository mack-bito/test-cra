import java.util.Scanner;
import service.LibraryService;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LibraryService libraryService = new LibraryService();

        libraryService.seedData();

        while (true) {
            System.out.println("\n===== Library App =====");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. View Books");
            System.out.println("4. View Members");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. Search Book");
            System.out.println("8. Delete Book");
            System.out.println("9. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter book id: ");
                int id = sc.nextInt();

                System.out.print("Enter title: ");
                String title = sc.nextLine(); // BUG: skips input

                System.out.print("Enter author: ");
                String author = sc.nextLine();

                libraryService.addBook(id, title, author);
            } else if (choice == 2) {
                System.out.print("Enter member id: ");
                int id = sc.nextInt();

                System.out.print("Enter member name: ");
                String name = sc.nextLine(); // BUG: skips input

                libraryService.addMember(id, name);
            } else if (choice == 3) {
                libraryService.viewBooks();
            } else if (choice == 4) {
                libraryService.viewMembers();
            } else if (choice == 5) {
                System.out.print("Enter book id: ");
                int bookId = sc.nextInt();

                System.out.print("Enter member id: ");
                int memberId = sc.nextInt();

                libraryService.issueBook(bookId, memberId);
            } else if (choice == 6) {
                System.out.print("Enter book id: ");
                int bookId = sc.nextInt();

                libraryService.returnBook(bookId);
            } else if (choice == 7) {
                System.out.print("Enter title to search: ");
                String title = sc.nextLine(); // BUG: skips input

                libraryService.searchBook(title);
            } else if (choice == 8) {
                System.out.print("Enter book id: ");
                int bookId = sc.nextInt();

                libraryService.deleteBook(bookId);
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
