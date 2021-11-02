package server;

import server.networking.SocketServer;

public class RunServer
{
  public static void main(String[] args)
  {
    SocketServer ss = new SocketServer();
    ss.startServer();
    System.out.println(" ->Server launched ...");
  }

}
