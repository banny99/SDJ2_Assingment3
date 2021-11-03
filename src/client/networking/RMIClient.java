package client.networking;

import server.networking.ChatServer_Remote;
import shared.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIClient implements Client
{
  private ChatServer_Remote serverStub;
  private PropertyChangeSupport changeSupport;

  public RMIClient()
  {
    super();
    changeSupport = new PropertyChangeSupport(this);

    try {
      serverStub = (ChatServer_Remote) Naming.lookup("rmi://localhost:1099/messenger");
      UnicastRemoteObject.exportObject(this, 0);
    } catch (NotBoundException | MalformedURLException | RemoteException e) {
      e.printStackTrace();
    }
  }

  @Override public void login(LoginObject lo)
  {
    String serverReply = "denied";

    try {
      serverReply = serverStub.rmiLogin(this, lo);
    } catch (RemoteException e) {
      System.err.println("Remote exception when trying to log in [RMIClient.login()]");
    }

    //when login approved
    if (!serverReply.equals("approved"))
      throw new DeniedLoginException(serverReply);
  }

  @Override public void sendMessage(MessageObject msg)
  {
    try {
      serverStub.rmiChat(msg);
    } catch (RemoteException e) {
      System.out.println("message failed to send ... [RMIClient.sendMessage()]");
    }
  }

  @Override public void receiveReply(MessageObject msg)
  {
    changeSupport.firePropertyChange("msg", null, msg);
  }

  @Override public void requestConnections()
  {
    try {
      ArrayList<LoginObject> connections = serverStub.getConnections();
      changeSupport.firePropertyChange("cnct", null, connections);
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  @Override public void updateConnections(ArrayList<LoginObject> connections) throws RemoteException
  {
    changeSupport.firePropertyChange("cnct", null, connections);
  }


  @Override public void addListener(String eventName, PropertyChangeListener listener)
  {
    changeSupport.addPropertyChangeListener(eventName, listener);
  }
  @Override public void removeListener(String eventName, PropertyChangeListener listener)
  {
    changeSupport.removePropertyChangeListener(eventName, listener);
  }


  @Override public void disconnect()
  {
    try {
      serverStub.disconnect(this);
    } catch (RemoteException e) {
      e.printStackTrace();
    }

    System.out.println("Client disconnected ...");
    System.exit(0);
  }
}
