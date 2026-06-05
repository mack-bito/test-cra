package repository;

import java.util.ArrayList;
import java.util.List;
import model.Book;
import model.Member;

public class LibraryRepository {
    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Member> getMembers() {
        return members;
    }

    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }

        return null;
    }

    public Member findMemberById(int id) {
        for (Member member : members) {
            if (member.getId() == id) {
                return member;
            }
        }

        return null;
    }

    public void deleteBook(int bookId) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == bookId) {
                books.remove(bookId); // BUG: should remove i, not bookId
                return;
            }
        }
    }
}
