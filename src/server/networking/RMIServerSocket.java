package server.networking;

import server.model.Password;
import server.model.Username;
import shared.LoginObject;
import shared.MessageObject;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServerSocket extends UnicastRemoteObject implements ChatServer_Remote
{

  public RMIServerSocket() throws RemoteException
  {
    super();
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

  @Override public void rmiChat(MessageObject messageObject) throws RemoteException
  {
    System.out.println("msg: " + messageObject.getMessageText());
  }

}
