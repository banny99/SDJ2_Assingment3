package client.view.login;

import client.model.LoginModel;
import shared.LoginObject;

public class LoginViewModel
{

  private final LoginModel loginModel;

  public LoginViewModel(LoginModel loginModel)
  {
    this.loginModel = loginModel;
  }


  public void loginBtnPressed(LoginObject lo)
  {
    loginModel.processLogin(lo);
  }


  public void disconnect()
  {
    loginModel.disconnect();
  }
}
