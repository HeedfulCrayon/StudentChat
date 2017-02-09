package studentChat;

import java.util.*;

/**
 * Created by Nate on 1/27/2017.
 * Initializes students, assigns them to a group and give students their responses
 * Launches windows for chat selection
 */
public final class ChatServer {

    private final List<Group> groups;

    // Calls the createStudentList function to create students, and then creates groups with 2 students in them
    private ChatServer() {
        HashSet<Student> students = createStudentList();
        groups = createGroups(students);
    }

    // Initializes chatServer and starts the chat method
    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.chat();
    }

    // Iterates through the groups assigning responses to students
    private void chat() {
        Dialogue dialogue = new Dialogue();
        for (Group group : groups) {
            Student student1 = group.getStudent1();
            Student student2 = group.getStudent2();
            String[] messages = dialogue.getMessages();
            for (int i = 0; i < dialogue.getMessages().length; i++) {
                addResponse(student1, messages[i++]);
                addResponse(student2, messages[i]);
            }
        }
        new GroupSelectorGUI(groups);
    }

    // Adds response to student and then prints out the student and message
    private void addResponse(Student student1, String message) {
        Response response = new Response(student1, message);
        student1.getResponses().add(response);
    }

    // Iterates through students, two at a time and assigns them to a group, then sorts the group by student 1
    private List<Group> createGroups(HashSet<Student> students) {
        List<Group> groups = new ArrayList<>();
        int numGroups = students.size() / 2;
        Iterator<Student> studentIterator = students.iterator();
        for (int i = 0; i < numGroups; i++) {
            Group group = new Group(studentIterator.next(),studentIterator.next());
            groups.add(group);
        }
        Collections.sort(groups);
        return groups;
    }

    // Creates the students
    private HashSet<Student> createStudentList() {
        HashSet<Student> students = new HashSet<>();
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
        return students;
    }
}
