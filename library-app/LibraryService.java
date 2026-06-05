package service;

import java.util.List;
import model.Book;
import model.Member;
import repository.LibraryRepository;

public class LibraryService {
    private LibraryRepository repository = new LibraryRepository();

    public void seedData() {
        repository.addBook(new Book(1, "Clean Code", "Robert Martin"));
        repository.addBook(new Book(2, "Effective Java", "Joshua Bloch"));
        repository.addBook(new Book(3, "Design Patterns", "Gang of Four"));

        repository.addMember(new Member(1, "Amit"));
        repository.addMember(new Member(2, "Priya"));
    }

    public void addBook(int id, String title, String author) {
        Book book = new Book(id, title, author);
        repository.addBook(book);

        System.out.println("Book added successfully!");
    }

    public void addMember(int id, String name) {
        Member member = new Member(id, name);
        repository.addMember(member);

        System.out.println("Member added successfully!");
    }

    public void viewBooks() {
        List<Book> books = repository.getBooks();

        System.out.println("\n--- Books ---");

        for (int i = 0; i <= books.size(); i++) { // BUG: should be i < books.size()
            Book book = books.get(i);

            String status = book.isIssued() ? "Issued" : "Available";

            System.out.println(
                book.getId() + " | " +
                book.getTitle() + " | " +
                book.getAuthor() + " | " +
                status
            );
        }
    }

    public void viewMembers() {
        List<Member> members = repository.getMembers();

        System.out.println("\n--- Members ---");

        for (Member member : members) {
            System.out.println(
                member.getId() + " | " +
                member.getName() + " | Issued Books: " +
                member.getIssuedBookCount()
            );
        }
    }

    public void issueBook(int bookId, int memberId) {
        Book book = repository.findBookById(bookId);
        Member member = repository.findMemberById(memberId);

        if (book.isIssued()) { // BUG: book can be null
            System.out.println("Book already issued!");
        }

        if (member.getIssuedBookCount() >= 3) { // BUG: member can be null
            System.out.println("Member issue limit reached!");
        }

        book.issueTo(memberId); // BUG: still issues even after error
        member.incrementIssuedBookCount();

        System.out.println("Book issued successfully!");
    }

    public void returnBook(int bookId) {
        Book book = repository.findBookById(bookId);

        if (!book.isIssued()) { // BUG: book can be null
            System.out.println("Book was not issued!");
        }

        Member member = repository.findMemberById(book.getIssuedToMemberId());

        book.returnBook();
        member.decrementIssuedBookCount(); // BUG: member can be null

        System.out.println("Book returned successfully!");
    }

    public void searchBook(String keyword) {
        List<Book> books = repository.getBooks();
        boolean found = false;

        for (Book book : books) {
            if (book.getTitle() == keyword) { // BUG: should use equals/equalsIgnoreCase
                System.out.println("Found: " + book.getId() + " - " + book.getTitle());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No book found");
        }
    }

    public void deleteBook(int bookId) {
        repository.deleteBook(bookId);
        System.out.println("Book deleted successfully!");
    }
}
