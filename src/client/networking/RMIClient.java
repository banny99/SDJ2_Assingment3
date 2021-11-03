package client.networking;

import server.networking.ChatServer_Remote;
import shared.DeniedLoginException;
import shared.LoginObject;
import shared.MessageObject;
import shared.TransferObject;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMIClient implements Client, ChatClient_Remote
{

  private LoginObject logedUser = null;
  private ChatServer_Remote serverStub;
  private PropertyChangeSupport changeSupport;

  public RMIClient()
  {
    changeSupport = new PropertyChangeSupport(this);


    try {
//      UnicastRemoteObject.exportObject(this, 1099);
//      Naming.rebind("client", this);

      serverStub = (ChatServer_Remote) Naming.lookup("messenger");
    } catch (NotBoundException | MalformedURLException | RemoteException e) {
      e.printStackTrace();
    }
  }

  @Override public void login(LoginObject lo)
  {
    String serverReply = "denied";

    try {
      serverReply = serverStub.rmiLogin(lo);
    } catch (RemoteException e) {
      System.err.println("Remote exception when trying to log in [RMIClient.login()]");
    }

    //when login approved
    if (serverReply.equals("approved"))
    {
      try {
        serverStub.addConnection(lo);
        logedUser = lo;
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }
    else
    {
      throw new DeniedLoginException(serverReply);
    }
  }

  @Override public void sendMessage(MessageObject msg)
  {
    try
    {
      serverStub.rmiChat(msg);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    System.out.println("RMI trying to send message");
  }

  @Override public void receiveReply(TransferObject transferObject)
  {

  }

  @Override public void disconnect()
  {
    System.out.println("RMI trying to disconnect");
    try {
      serverStub.removeConnection(logedUser);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  @Override public void requestConnections()
  {
    System.out.println("RMI trying to request connections");
    try {
      ArrayList<LoginObject> connections = serverStub.getConnections();
      changeSupport.firePropertyChange("cnct", null, connections);
      System.out.println(connections);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  @Override public void addListener(String eventName, PropertyChangeListener listener)
  {
    changeSupport.addPropertyChangeListener(eventName, listener);
  }
  @Override public void removeListener(String eventName, PropertyChangeListener listener)
  {
    changeSupport.removePropertyChangeListener(eventName, listener);
  }
}
