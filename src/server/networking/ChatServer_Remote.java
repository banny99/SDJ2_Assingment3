package server.networking;

import shared.LoginObject;
import shared.MessageObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer_Remote extends Remote
{
  String rmiLogin(LoginObject loginObject) throws RemoteException;
  void rmiChat(MessageObject messageObject) throws RemoteException;
}
