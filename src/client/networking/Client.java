package client.networking;

import shared.LoginObject;
import shared.MessageObject;
import shared.TransferObject;

import java.beans.PropertyChangeListener;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Client extends Remote
{
  void login(LoginObject lo) throws RemoteException;
  void sendMessage(MessageObject msg) throws RemoteException;
  void receiveReply(MessageObject msg) throws RemoteException;
  void disconnect() throws RemoteException;
  void requestConnections() throws RemoteException;

  void addListener(String eventName, PropertyChangeListener listener) throws RemoteException;
  void removeListener(String eventName, PropertyChangeListener listener) throws RemoteException;

  void updateConnections(ArrayList<LoginObject> connections) throws RemoteException;
}
