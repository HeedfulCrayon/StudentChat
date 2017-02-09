package studentChat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Nate on 1/25/2017.
 * Class that represents a student in object form
 */
public final class Student implements Comparable<Student> {
    private final String firstName;
    private final String lastName;
    private Double score;
    private final List<Response> responses;

    // Takes a first and last name and assigns them firstName and lastName
    // Creates an empty responses arrayList

    /**
     * Creates student with an empty responses arrayList
     * @param FirstName student's first name
     * @param LastName student's last name
     */
    public Student(String FirstName, String LastName) {
        this.firstName = FirstName;
        this.lastName = LastName;
        responses = new ArrayList<>();
    }

    // Returns the student's first name
    public String getFirstName() {
        return firstName;
    }

    // Returns the student's last name
    public String getLastName() {
        return lastName;
    }

    // Returns the student's responses arrayList
    public List<Response> getResponses() {
        return responses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (firstName != null ? !firstName.equals(student.firstName) : student.firstName != null) return false;
        return lastName != null ? lastName.equals(student.lastName) : student.lastName == null;

    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName);
    }

    // Formats the student's name into first name then last name
    @Override
    public String toString() {
        return String.format("%s %s: ", firstName, lastName);
    }

    // Compares students by last then first name for sorting purposes
    @Override
    public int compareTo(Student personIn) {
        String person = String.format("%s %s", this.getLastName(), this.getFirstName());
        String other = String.format("%s %s", personIn.getLastName(), personIn.getFirstName());
        return person.compareToIgnoreCase(other);
    }
}
