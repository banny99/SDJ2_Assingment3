package networking;

import shared.LoginObject;
import shared.MessageObject;
import utility.observer.listener.RemoteListener;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Client_Remote extends RemoteListener<MessageObject, MessageObject>
{
  void updateConnections(ArrayList<LoginObject> connections) throws RemoteException;
  void receiveReply(MessageObject msg) throws RemoteException;
  String getUsername() throws RemoteException;
}
