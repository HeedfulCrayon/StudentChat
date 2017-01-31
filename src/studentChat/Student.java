package studentChat;

import java.util.ArrayList;
import java.util.List;

/*
    Created by Nate on 1/25/2017.
    Class that represents a student in object form
*/
public final class Student implements Comparable<Student> {
    private final String firstName;
    private final String lastName;
    private Double score;
    private final List<Response> responses;

    // Constructor
    // Takes a first and last name and assigns them firstName and lastName
    // Creates an empty responses arrayList
    public Student(String FirstName, String LastName) {
        this.firstName = FirstName;
        this.lastName = LastName;
        responses = new ArrayList<>();
    }

    // Basic getter
    // Returns the student's first name
    public String getFirstName() {
        return firstName;
    }

    // Basic getter
    // Returns the student's last name
    public String getLastName() {
        return lastName;
    }

    // Basic getter
    // Returns the student's responses arrayList
    public List<Response> getResponses() {
        return responses;
    }

    // Basic setter
    // Sets the student's score
    public void setScore(Double score) {
        this.score = score;
    }

    // Basic getter
    // Returns the student's score
    public Double getScore() {
        return score;
    }

    // equals override
    // Determines if students are the same by the contents of their first and last name
    @Override
    public boolean equals(Object other) {
        if (other.getClass() != Student.class) {
            return false;
        }
        Student otherStudent = (Student) other;
        return otherStudent.firstName.equals(this.firstName) && otherStudent.lastName.equals(this.lastName);
    }

    // hashCode override
    // Generic hashCode function
    @Override
    public int hashCode() {
        return 0;
    }

    // toString override
    // Formats the student's name into first name then last name
    @Override
    public String toString() {
        return String.format("%s %s: ", firstName, lastName);
    }

    // compareTo override
    // Compares students by last then first name for sorting purposes
    @Override
    public int compareTo(Student personIn) {
        String person = String.format("%s %s", this.getLastName(), this.getFirstName());
        String other = String.format("%s %s", personIn.getLastName(), personIn.getFirstName());
        return person.compareToIgnoreCase(other);
    }
}
