package studentChat;
/*
    Created by Nate on 1/25/2017.
    Class that is essentially a list of 2 to 3 students
 */
public final class Group implements Comparable<Group> {
    private Student student1;
    private Student student2;

    // Constructor.  Takes 2 students and adds them to the group
    public Group(Student student1, Student student2) {
        this.student1 = student1;
        this.student2 = student2;
    }

    // Basic getter
    // Returns student1 from the group
    public Student getStudent1() {
        return student1;
    }

    // Basic getter
    // Returns student2 from the group
    public Student getStudent2() {
        return student2;
    }

    // toString override
    // Prints student names and student position (student 1, student 2, etc.)
    @Override
    public String toString() {
        return String.format("Student 1: %s\t\tStudent 2: %s", student1.toString(), student2.toString());
    }

    // equals override
    // Determines if a group is equal by the student1 object
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Group.class) {
            return false;
        }
        Group other = (Group) obj;
        return other.student1.equals(this.student1);
    }

    // hashCode override
    // Generice hashCode function
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    // compareTo override
    // Compares groups by the student1 value for sorting purposes
    @Override
    public int compareTo(Group other) {
        return this.student1.compareTo(other.student1);
    }
}
