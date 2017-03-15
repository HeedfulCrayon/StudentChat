package studentChat;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Nate on 3/14/2017.
 */
public class ClientConnection {
    private String userName;
    private Socket socket;
    private PrintWriter output;

    public ClientConnection(String user,Socket sock, PrintWriter out){
        userName = user;
        socket = sock;
        output = out;
    }

    public String getUserName(){
        return userName;
    }

    public void sendMessage(String msg){
        output.println(msg);
        output.flush();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientConnection that = (ClientConnection) o;

        return userName.equals(that.userName);

    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }
}
