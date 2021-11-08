package networking;

import shared.*;
import utility.observer.event.ObserverEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIClient implements Client, Client_Remote
{
  private ChatServer_Remote serverStub;
  private PropertyChangeSupport changeSupport;
  private LoginObject loggedUser = null;

  public RMIClient()
  {
    super();
    changeSupport = new PropertyChangeSupport(this);

    try {
      serverStub = (ChatServer_Remote) Naming.lookup("rmi://localhost:1099/messenger");
      UnicastRemoteObject.exportObject(this, 0);

//      serverStub.addListener(this);
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
    if (serverReply.equals("approved"))
    {
      loggedUser = lo;
      sendMessage(new MessageObject("  '" + loggedUser.getUsername() + " -connected'", loggedUser.getUsername(), new ArrayList<>()));
    }
    else
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

  @Override public String getUsername() throws RemoteException
  {
    return loggedUser.getUsername();
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
      if (loggedUser != null)
        serverStub.rmiChat(new MessageObject("  '" + loggedUser.getUsername() + " -disconnected'", loggedUser.getUsername(), new ArrayList<>()));
      serverStub.disconnect(this);
    } catch (RemoteException e) {
      e.printStackTrace();
    }

    System.out.println("Client disconnected ...");
    System.exit(0);
  }

  @Override public void propertyChange(
      ObserverEvent<MessageObject, MessageObject> event) throws RemoteException
  {
    if (event.getValue2().getChatMembers().isEmpty() || event.getValue2().getChatMembers().contains(loggedUser))
      changeSupport.firePropertyChange("msg", null, event.getValue2());
  }
}
