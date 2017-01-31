package studentChat;

/*
    Created by Nate on 1/27/2017.
    Class that contains and returns student responses
*/
public final class Response {

    // student that owns the response
    private final Student owner;

    // Message that is in the response
    private final String message;

    // Constructor
    // Requires a student, and a string
    // Sets the student as the owner and the string as their message
    public Response(Student owner, String message) {
        this.owner = owner;
        this.message = message;
    }

    // Basic getter
    // Returns the owner of the response
    public Student getOwner() {
        return owner;
    }

    // Basic getter
    // Returns the message in the response
    public String getMessage() {
        return message;
    }

    // hashCode override
    // Generic hashCode function
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    // equals override
    // Determines if responses are equal based on the contents of the message
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Response.class) {
            return false;
        }
        Response other = (Response) obj;
        return message.equals(other.message);
    }

    // toString override
    // Formats the response so that the owner is in the string first and then the message
    @Override
    public String toString() {
        return String.format("%s %s", owner, message);
    }
}

