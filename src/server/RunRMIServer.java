package server;

import server.networking.RMIServerSocket;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RunRMIServer
{
  public static void main(String[] args)
  {
    try
    {
      startRegistry();
      RMIServerSocket rmiServerSocket = new RMIServerSocket();
      rmiServerSocket.start();
      System.out.println("Server launched ...");
    }
    catch (MalformedURLException | RemoteException e)
    {
//      e.printStackTrace();
      System.out.println("Server launch failed ...");
    }
  }

  private static void startRegistry()
  {
    try {
      Registry registry = LocateRegistry.createRegistry(1099);
      System.out.println("Registry created ...");
    } catch (RemoteException e) {
//      e.printStackTrace();
      System.out.println("Registry already started? " + e.getMessage());
    }
  }
}
