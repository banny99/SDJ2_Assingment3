package client.model;

import shared.MessageObject;
import shared.Observable;

public interface Model extends Observable
{
  void processMessage(MessageObject msg);
  void disconnect();
  void requestConnections();
}
