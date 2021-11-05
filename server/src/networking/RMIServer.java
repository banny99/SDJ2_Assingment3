package networking;

import model.Password;
import model.Username;
import shared.LoginObject;
import shared.MessageObject;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIServer extends UnicastRemoteObject implements ChatServer_Remote
{

  private ArrayList<LoginObject> connections;
  private ArrayList<Client_Remote> clientStubs;

  public RMIServer() throws RemoteException
  {
    super();
    connections = new ArrayList<>();
    clientStubs = new ArrayList<>();
  }

  public void start() throws MalformedURLException, RemoteException
  {
    Naming.rebind("messenger", this);
  }


  @Override public String rmiLogin(Client_Remote client, LoginObject loginObject) throws RemoteException
  {
    String reply;
    try
    {
      new Username(loginObject.getUsername());
      new Password(loginObject.getPassword());
      reply = "approved";
    }
    catch (IllegalArgumentException e){
      reply = e.getMessage();
    }

    if (reply.equals("approved"))
    {
      addConnection(client, loginObject);
      for(Client_Remote c : clientStubs)
        c.updateConnections(connections);
    }

    return reply;
  }
  private void addConnection(Client_Remote client, LoginObject loginObject)
  {
    connections.add(loginObject);
    clientStubs.add(client);
  }
  @Override public ArrayList<LoginObject> getConnections() throws RemoteException
  {
    return connections;
  }


  @Override public void rmiChat(MessageObject messageObject) throws RemoteException
  {
    if (messageObject.getChatMembers().isEmpty())
    {
      for(Client_Remote c : clientStubs)
        c.receiveReply(messageObject);
    }
    else
    {
      for (LoginObject lo : messageObject.getChatMembers())
        clientStubs.get(connections.indexOf(lo)).receiveReply(messageObject);
    }
  }


  @Override public void disconnect(Client_Remote client) throws RemoteException
  {
    if (clientStubs.contains(client))
    {
      removeConnection(client);
      for (Client_Remote c : clientStubs)
        c.updateConnections(connections);
    }
  }
  private void removeConnection(Client_Remote client)
  {
    int index = clientStubs.indexOf(client);
    connections.remove(index);
    clientStubs.remove(client);
  }

}
