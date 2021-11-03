package client.core;

import client.networking.Client;
import client.networking.RMIClient;

public class ClientFactory
{

  private Client rmiClient;

  public Client getClient()
  {
    if (rmiClient == null){
      rmiClient = new RMIClient();
    }
    return rmiClient;
  }
}
