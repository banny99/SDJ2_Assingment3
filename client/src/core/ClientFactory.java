package core;

import networking.Client;
import networking.RMIClient;

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
