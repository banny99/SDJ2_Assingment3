package shared;

import java.util.ArrayList;

public class ConnectionsObject
{
  private final ArrayList<LoginObject> currConnections;

  public ConnectionsObject(ArrayList<LoginObject> currConnections)
  {
    this.currConnections = currConnections;
  }

  public ArrayList<LoginObject> getCurrConnections()
  {
    return currConnections;
  }

  @Override public String toString()
  {
    return "ConnectionsObject{" + "currConnections=" + currConnections + '}';
  }
}
