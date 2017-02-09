package studentChat;

import java.util.Objects;

/*
    Created by Nate on 1/25/2017.
    Class that is essentially a list of 2 to 3 students
 */
public final class Group implements Comparable<Group> {
    private Student student1;
    private Student student2;

    /**
     * Creates a group of two students
     * @param student1 first student
     * @param student2 second student
     */
    public Group(Student student1, Student student2) {
        this.student1 = student1;
        this.student2 = student2;
    }

    // Returns student1 from the group
    public Student getStudent1() {
        return student1;
    }

    // Returns student2 from the group
    public Student getStudent2() {
        return student2;
    }

    // Prints student names and student position (student 1, student 2, etc.)
    @Override
    public String toString() {
        return String.format("Student 1: %s\t\tStudent 2: %s", student1.toString(), student2.toString());
    }

    // Determines if a group is equal by the student1 object
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Group.class) {
            return false;
        }
        Group other = (Group) obj;
        return other.student1.equals(this.student1);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this);
    }

    // Compares groups by the student1 value for sorting purposes
    @Override
    public int compareTo(Group other) {
        return this.student1.compareTo(other.student1);
    }
}
