package server.networking;

import client.networking.ChatClient_Remote;
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
  private ArrayList<ChatClient_Remote> clientStubs;

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


  @Override public String rmiLogin(LoginObject loginObject) throws RemoteException
  {
    try
    {
      new Username(loginObject.getUsername());
      new Password(loginObject.getPassword());
      return  "approved";
    }
    catch (IllegalArgumentException e){
      return e.getMessage();
    }
  }

  @Override public ArrayList<LoginObject> getConnections() throws RemoteException
  {
    return connections;
  }

  @Override public void rmiChat(MessageObject messageObject) throws RemoteException
  {
    System.out.println("msg: " + messageObject.getMessageText());
  }


  @Override public void addConnection(LoginObject loginObject)
  {
    connections.add(loginObject);
//    clientStubs.add(clientStub);
  }
  @Override public void removeConnection(LoginObject loginObject)
  {
    connections.remove(loginObject);
//    clientStubs.remove(clientStub);
  }
}
