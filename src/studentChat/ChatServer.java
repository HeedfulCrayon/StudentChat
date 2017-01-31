package studentChat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 Created by Nate on 1/27/2017.
 Main application for chat program
 Initializes students, assigns them to a group and give students their responses
 Prints student responses as a conversation
*/
public final class ChatServer {

    private final List<Group> groups;

    // Constructor
    // Calls the createStudentList function to create students, and then creates groups with 2 students in them
    private ChatServer() {
        List<Student> students = createStudentList();

        groups = createGroups(students);
    }

    // Main function
    // Initializes chatServer and starts the chat method
    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();

        chatServer.chat();
    }

    // Iterates through the groups assigning responses to students
    private void chat() {
        Dialogue dialogue = new Dialogue();
        int counter = 1;
        for (Group group : groups) {
            System.out.println("Group " + counter++);

            Student student1 = group.getStudent1();
            Student student2 = group.getStudent2();
            String[] messages = dialogue.getMessages();
            for (int i = 0; i < dialogue.getMessages().length; i++) {
                addResponse(student1, messages[i++]);
                addResponse(student2, messages[i]);
            }
        }
    }

    // Adds response to student and then prints out the student and message
    private void addResponse(Student student1, String message) {
        Response response = new Response(student1, message);
        student1.getResponses().add(response);
        System.out.println(response);
    }

    // Iterates through students, two at a time and assigns them to a group, then sorts the group by student 1
    private List<Group> createGroups(List<Student> students) {
        List<Group> groups = new ArrayList<>();

        for (int i = 0; i < students.size(); i++) {
            Group group = new Group(students.get(i++), students.get(i));
            groups.add(group);
        }
        Collections.sort(groups);
        return groups;
    }

    // Creates the students and then sorts them alphabetically by last name
    private List<Student> createStudentList() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Ethan", "Brown"));
        students.add(new Student("Nathan", "Borup"));
        students.add(new Student("Michael", "Cullimore"));
        students.add(new Student("Kendra", "Koester"));
        students.add(new Student("Cody", "May"));
        students.add(new Student("Brieanna", "Miller"));
        students.add(new Student("Rizwan", "Mohammed"));
        students.add(new Student("Lauren", "Ribeiro"));
        students.add(new Student("Tyler", "Toponce"));
        students.add(new Student("Trevor", "Marsh"));
        Collections.sort(students);
        return students;
    }
}
