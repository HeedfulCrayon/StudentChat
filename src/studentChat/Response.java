package studentChat;

/**
 * Created by Nate on 1/27/2017.
 * Class that contains and returns student responses
 */
public final class Response {

    private final Student owner;

    private final String message;

    /**
     * Sets the student as the owner and the string as their message
     * @param owner Owner of response
     * @param message message sent
     */
    public Response(Student owner, String message) {
        this.owner = owner;
        this.message = message;
    }

    // Returns the owner of the response
    public Student getOwner() {
        return owner;
    }

    // Returns the message in the response
    public String getMessage() {
        return message;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    // Determines if responses are equal based on the contents of the message
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Response.class) {
            return false;
        }
        Response other = (Response) obj;
        return message.equals(other.message);
    }

    // Formats the response so that the owner is in the string first and then the message
    @Override
    public String toString() {
        return String.format("%s\n %s", owner, message);
    }
}

