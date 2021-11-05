package networking;

import shared.LoginObject;
import shared.MessageObject;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface Client_Remote extends Remote
{
  void updateConnections(ArrayList<LoginObject> connections) throws RemoteException;
  void receiveReply(MessageObject msg) throws RemoteException;
}
