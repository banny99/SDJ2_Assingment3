package server.networking;

import client.networking.Client;
import shared.LoginObject;
import shared.MessageObject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ChatServer_Remote extends Remote
{
  String rmiLogin(Client client, LoginObject loginObject) throws RemoteException;
  ArrayList<LoginObject> getConnections() throws RemoteException;
  void rmiChat(MessageObject messageObject) throws RemoteException;

  void disconnect(Client client) throws RemoteException;
}
