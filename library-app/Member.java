package model;

public class Member {
    private int id;
    private String name;
    private int issuedBookCount;

    public Member(int id, String name) {
        this.id = id;
        this.name = name;
        this.issuedBookCount = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIssuedBookCount() {
        return issuedBookCount;
    }

    public void incrementIssuedBookCount() {
        issuedBookCount++;
    }

    public void decrementIssuedBookCount() {
        issuedBookCount--;
    }
}
