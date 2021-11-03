package server.networking;

import client.networking.ChatClient_Remote;
import shared.LoginObject;
import shared.MessageObject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatServer_Remote extends Remote
{
  String rmiLogin(LoginObject loginObject) throws RemoteException;
  ArrayList<LoginObject> getConnections() throws RemoteException;
  void rmiChat(MessageObject messageObject) throws RemoteException;

  public void addConnection(LoginObject loginObject) throws RemoteException;
  public void removeConnection(LoginObject loginObject) throws RemoteException;
}
