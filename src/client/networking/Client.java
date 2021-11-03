package client.networking;

import shared.LoginObject;
import shared.MessageObject;
import shared.Observable;

public interface Client extends Observable
{
  void login(LoginObject lo);
  void sendMessage(MessageObject msg);
  void requestConnections();
  void disconnect();
}
