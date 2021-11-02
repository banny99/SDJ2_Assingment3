package server.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer
{

  private final ConnectionPool connectionPool;

  public SocketServer()
  {
    connectionPool = new ConnectionPool();
  }

  public void startServer()
  {
    try
    {
      ServerSocket welcomeSocket = new ServerSocket(4444);

      while (true){
        System.out.println("Server listening for client requests ...");

        Socket socket = welcomeSocket.accept();
        ServerSocketThread tempThread = new ServerSocketThread(socket, connectionPool);
        tempThread.start();
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

}
