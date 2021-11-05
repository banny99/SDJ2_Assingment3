package model;

import shared.LoginObject;

public interface LoginModel
{
  void processLogin(LoginObject lo);
  void disconnect();
}
