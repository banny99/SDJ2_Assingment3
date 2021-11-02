package server.networking;

import shared.ConnectionsObject;
import shared.LoginObject;
import shared.MessageObject;
import java.util.ArrayList;

public class ConnectionPool
{

  private final ArrayList<ServerSocketThread> listeners = new ArrayList<>();
  private final ArrayList<LoginObject> activeUsers = new ArrayList<>();

  public void addListener(ServerSocketThread t, LoginObject user)
  {
    listeners.add(t);
    activeUsers.add(user);

    broadcastActiveUsers();
  }
  public void removeListener(ServerSocketThread t)
  {
    activeUsers.remove(listeners.indexOf(t));
    listeners.remove(t);

    broadcastActiveUsers();
  }

  public void broadcastMessage(MessageObject messageObject)
  {
    ArrayList<LoginObject> tempMembers = messageObject.getChatMembers();

    if (tempMembers.isEmpty())
    {
      System.out.println(" -broadcasting msg to: everyone");

      for (ServerSocketThread t : listeners)
      {
        t.sendMessage(messageObject);
      }
    }

    else
    {
      System.out.print(" -broadcasting msg to: ");

      for (LoginObject l : tempMembers) {
        for (int i=0; i<listeners.size(); i++) {
          if (l.equals(activeUsers.get(i)))
          {
            listeners.get(i).sendMessage(messageObject);
            System.out.print(l.getUsername() + ", ");
          }
        }
      }
      System.out.println();
    }
  }


  public void broadcastActiveUsers()
  {
    ArrayList<LoginObject> connectionsCopy = new ArrayList<>(activeUsers);
    for (ServerSocketThread t : listeners)
    {
      t.updateConnections(new ConnectionsObject(connectionsCopy));
    }
  }

  public void broadcastActiveUsers(ServerSocketThread t)
  {
    ArrayList<LoginObject> connectionsCopy = new ArrayList<>(activeUsers);
    t.updateConnections(new ConnectionsObject(connectionsCopy));
  }
}
