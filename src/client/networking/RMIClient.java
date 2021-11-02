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

public class RMIClient implements Client
{

  private ChatServer_Remote serverStub;
  private PropertyChangeSupport changeSupport;

  public RMIClient()
  {
    changeSupport = new PropertyChangeSupport(this);
    try {
      serverStub = (ChatServer_Remote) Naming.lookup("messenger");
    } catch (NotBoundException | MalformedURLException | RemoteException e) {
      e.printStackTrace();
    }
  }

  @Override public void login(LoginObject lo)
  {
    try {
      String serverReply = serverStub.rmiLogin(lo);

      //when login approved ->run communication thread
      if (!serverReply.equals("approved"))
        throw new DeniedLoginException(serverReply);

    } catch (RemoteException e) {
      e.printStackTrace();
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
  }

  @Override public void requestConnections()
  {
    System.out.println("RMI trying to request connections");
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
