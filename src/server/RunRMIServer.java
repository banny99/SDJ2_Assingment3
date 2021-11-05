package server;

import server.networking.RMIServer;
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
      RMIServer rmiServerSocket = new RMIServer();
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
