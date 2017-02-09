package studentChat;

/*
  Created by Nate on 1/27/2017.
  Class that contains text list of all responses for students
*/
public final class Dialogue {
    private final String[] messages = {
            "Who are you?",
            "No one of consequence.",
            "I must know.",
            "Get used to disappointment.",
            "You seem a decent fellow, I hate to kill you.",
            "You seem a decent fellow, I hate to die",
            "We face each other as God intended, No tricks, no weapons, skill against skill alone.",
            "You mean you'll put down your rock and I'll put down my sword and we'll try to kill each other like civilized people, is that it?",
            "Why do you wear a mask and hood?",
            "I think everybody will in the near future, they're terribly comfortable."
    };

    // Returns the messages string array
    public String[] getMessages() {
        return messages;
    }
}
