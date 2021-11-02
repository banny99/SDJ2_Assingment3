package client.core;

import client.model.*;

public class ModelFactory
{

  private final ClientFactory clientFactory;
  public ModelFactory(ClientFactory cf)
  {
    clientFactory = cf;
  }


  private LoginModel loginModel;
  public LoginModel getLoginModel()
  {
    if (loginModel==null)
      loginModel = new LoginModelManager(clientFactory.getClient());
    return loginModel;
  }

  private Model model;
  public Model getModel()
  {
    if (model == null)
      model = new ModelManager(clientFactory.getClient());
    return model;
  }
}
