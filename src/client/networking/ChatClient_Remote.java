package client.networking;

import shared.MessageObject;
import java.rmi.Remote;

public interface ChatClient_Remote extends Remote
{
  public void sendMessage(MessageObject msg) throws RuntimeException;
}
