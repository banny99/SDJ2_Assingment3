package model;

import networking.Client;
import shared.LoginObject;

public class LoginModelManager implements LoginModel
{
  private final Client client;

  public LoginModelManager(Client c)
  {
    client = c;
  }


  @Override public void processLogin(LoginObject lo)
  {
    client.login(lo);
  }


  @Override public void disconnect()
  {
    client.disconnect();
  }
}
