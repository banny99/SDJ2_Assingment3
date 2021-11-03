package client.model;

import client.networking.Client;
import shared.LoginObject;

import java.rmi.RemoteException;

public class LoginModelManager implements LoginModel
{
  private final Client client;

  public LoginModelManager(Client c)
  {
    client = c;
  }


  @Override public void processLogin(LoginObject lo)
  {
    try {
      client.login(lo);
    } catch (RemoteException e) {
      System.err.println("Login attempt failed ... [LoginModelManager.processLogin()]");
    }
  }


  @Override public void disconnect()
  {
    try {
      client.disconnect();
    } catch (RemoteException e) {
      System.out.println("Disconnection failed ... [LoginModelManager.disconnect()]");;
    }
  }
}
