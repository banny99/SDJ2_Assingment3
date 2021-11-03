package client.model;

import client.networking.Client;
import shared.LoginObject;
import shared.MessageObject;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ModelManager implements Model
{
  private final Client client;
  private final PropertyChangeSupport changeSupport;
  private ArrayList<LoginObject> updatedConnections;

  public ModelManager(Client client)
  {
    this.client = client;
    changeSupport = new PropertyChangeSupport(this);

    //subscription
    try {
      this.client.addListener("msg", this::receiveMsg);
      this.client.addListener("cnct", this::updateConnections);
    } catch (RemoteException e) {
      System.err.println("adding listeners failed ... [ModelManager.ModelManager()]");
    }

  }


  @Override public void requestConnections()
  {
    if (updatedConnections == null)
    {
      try {
        client.requestConnections();
      } catch (RemoteException e) {
        System.out.println("requesting connections failed ... [ModelManager.requestConnections()]");
      }
    }
    else
      changeSupport.firePropertyChange("cnct", null, updatedConnections);
  }

  @Override public void processMessage(MessageObject msg)
  {
    try {
      client.sendMessage(msg);
    } catch (RemoteException e) {
      System.err.println("message sending failed ... [ModelManager.processMessage()]");
    }
  }

  //listeners update-methods
  private void updateConnections(PropertyChangeEvent evt)
  {
    updatedConnections = (ArrayList<LoginObject>) evt.getNewValue();
    changeSupport.firePropertyChange(evt);
  }

  private void receiveMsg(PropertyChangeEvent evt)
  {
    changeSupport.firePropertyChange(evt);
  }


  //subject subscribing-methods
  @Override public void addListener(String eventName, PropertyChangeListener listener)
  {
    changeSupport.addPropertyChangeListener(eventName, listener);
  }
  @Override public void removeListener(String eventName, PropertyChangeListener listener)
  {
    changeSupport.removePropertyChangeListener(eventName, listener);
  }


  @Override public void disconnect()
  {
    try {
      client.disconnect();
    } catch (RemoteException e) {
      System.out.println("Disconnection failed ... [ModelManager.disconnect()]");
    }
  }

}
