package model;

public class Book {
    private int id;
    private String title;
    private String author;
    private boolean issued;
    private int issuedToMemberId;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.issued = false;
        this.issuedToMemberId = -1;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return issued;
    }

    public int getIssuedToMemberId() {
        return issuedToMemberId;
    }

    public void issueTo(int memberId) {
        this.issued = true;
        this.issuedToMemberId = memberId;
    }

    public void returnBook() {
        this.issued = false;
        this.issuedToMemberId = -1;
    }
}
