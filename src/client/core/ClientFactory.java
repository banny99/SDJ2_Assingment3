package client.core;

import client.networking.Client;
import client.networking.ClientSocket;
import client.networking.RMIClient;

import java.net.UnknownHostException;

public class ClientFactory
{
//  private Client client;
//
//  public Client getClient()
//  {
//    if (client == null){
//      try
//      {
//        client = new ClientSocket();
//      }
//      catch (UnknownHostException e)
//      {
//        e.printStackTrace();
//      }
//    }
//    return client;
//  }

  private Client rmiClient;

  public Client getClient()
  {
    if (rmiClient == null){
      rmiClient = new RMIClient();
    }
    return rmiClient;
  }
}
