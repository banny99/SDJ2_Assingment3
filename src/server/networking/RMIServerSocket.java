package server.networking;

import client.networking.Client;
import server.model.Password;
import server.model.Username;
import shared.LoginObject;
import shared.MessageObject;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIServerSocket extends UnicastRemoteObject implements ChatServer_Remote
{

  private ArrayList<LoginObject> connections;
  private ArrayList<Client> clientStubs;

  public RMIServerSocket() throws RemoteException
  {
    super();
    connections = new ArrayList<>();
    clientStubs = new ArrayList<>();
  }

  public void start() throws MalformedURLException, RemoteException
  {
    Naming.rebind("messenger", this);
  }


  @Override public String rmiLogin(Client client, LoginObject loginObject) throws RemoteException
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
      for(Client c : clientStubs)
        c.updateConnections(connections);
    }

    return reply;
  }
  private void addConnection(Client client, LoginObject loginObject)
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
      for(Client c : clientStubs)
        c.receiveReply(messageObject);
    }
    else
    {
      for (LoginObject lo : messageObject.getChatMembers())
        clientStubs.get(connections.indexOf(lo)).receiveReply(messageObject);
    }
  }


  @Override public void disconnect(Client client) throws RemoteException
  {
    if (clientStubs.contains(client))
    {
      removeConnection(client);
      for (Client c : clientStubs)
        c.updateConnections(connections);
    }
  }
  private void removeConnection(Client client)
  {
    int index = clientStubs.indexOf(client);
    connections.remove(index);
    clientStubs.remove(client);
  }

}
