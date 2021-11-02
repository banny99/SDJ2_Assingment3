package client.core;

import client.networking.Client;
import client.networking.ClientSocket;

import java.net.UnknownHostException;

public class ClientFactory
{
  private Client client;

  public Client getClient()
  {
    if (client == null){
      try
      {
        client = new ClientSocket();
      }
      catch (UnknownHostException e)
      {
        e.printStackTrace();
      }
    }
    return client;
  }
}
