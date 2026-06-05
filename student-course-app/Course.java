package model;

public class Course {
    private int id;
    private String name;
    private int capacity;
    private int enrolledCount;

    public Course(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.enrolledCount = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }

    public void incrementEnrolledCount() {
        enrolledCount++;
    }

    public void decrementEnrolledCount() {
        enrolledCount--;
    }

    public boolean isFull() {
        return enrolledCount > capacity; // BUG: should be >=
    }
}
