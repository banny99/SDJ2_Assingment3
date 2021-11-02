package server.networking;

import com.google.gson.Gson;
import server.model.Password;
import server.model.Username;
import shared.ConnectionsObject;
import shared.LoginObject;
import shared.MessageObject;
import shared.TransferObject;
import java.io.*;
import java.net.Socket;

public class ServerSocketThread extends Thread
{
  private final Socket socket;
  private DataInputStream inputStream;
  private DataOutputStream outputStream;
  private final ConnectionPool connectionPool;
  private final Gson gson;

  private LoginObject loggedUser;

  public ServerSocketThread(Socket socket, ConnectionPool connectionPool)
  {
    this.socket = socket;
    this.connectionPool = connectionPool;
    gson = new Gson();

    try
    {
      inputStream = new DataInputStream(socket.getInputStream());
      outputStream = new DataOutputStream(socket.getOutputStream());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  @Override public void run()
  {
    System.out.println(" ->Server communication-socket running ...");

    if (login())
    {
      connectionPool.addListener(this, loggedUser);
      System.out.println(" ->user logged in");

      chat();
    }
    System.out.println(" ->client disconnected : " + socket.getPort());
  }


  private boolean login()
  {
    String reply = "";

    while (!reply.equals("approved"))
    {
      try
      {
        String jsonRequest = inputStream.readUTF();
        TransferObject transferObject = gson.fromJson(jsonRequest, TransferObject.class);

        //Logging in
        if (transferObject.getType().equals("LO"))
        {
          loggedUser = gson.fromJson(transferObject.getNestedObjectJson(), LoginObject.class);
          System.out.println(loggedUser);

          try
          {
            new Username(loggedUser.getUsername());
            new Password(loggedUser.getPassword());
            reply = "approved";
          }
          catch (IllegalArgumentException e){
            reply = e.getMessage();
          }

          outputStream.writeUTF(reply);
        }

        //exit - app closing before log in approved
        else
        {
          return false;
        }

      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }

    return true;
  }


  private void chat()
  {
    System.out.println(" ->chat");
    try
    {
      while (true)
      {
        String jsonRequest = inputStream.readUTF();
        TransferObject transferObject = gson.fromJson(jsonRequest, TransferObject.class);

        //chat = message sent
        if (transferObject.getType().equals("MSG"))
        {
          MessageObject messageObject = gson.fromJson(transferObject.getNestedObjectJson(), MessageObject.class);
          System.out.println(messageObject);
          connectionPool.broadcastMessage(messageObject);
        }
        //connections update
        else if (transferObject.getType().equals("CNCT"))
        {
          connectionPool.broadcastActiveUsers(this);
        }
        //exit = disconnecting
        else
        {
          connectionPool.removeListener(this);
          break;
        }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }


  //subscriber's "update()" method
  public void sendMessage(MessageObject messageObject)
  {
    try
    {
      TransferObject transferObject = new TransferObject("MSG", gson.toJson(messageObject));
      outputStream.writeUTF(gson.toJson(transferObject));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public void updateConnections(ConnectionsObject connectionsObject)
  {
    try
    {
      TransferObject transferObject = new TransferObject("CNCT", gson.toJson(connectionsObject));
      outputStream.writeUTF(gson.toJson(transferObject));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
