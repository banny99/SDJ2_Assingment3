package client.model;

import client.networking.Client;
import shared.LoginObject;
import shared.MessageObject;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
    this.client.addListener("msg", this::receiveMsg);
    this.client.addListener("cnct", this::updateConnections);
  }


  @Override public void requestConnections()
  {
    if (updatedConnections == null)
      client.requestConnections();
    else
      changeSupport.firePropertyChange("cnct", null, updatedConnections);
  }

  @Override public void processMessage(MessageObject msg)
  {
    client.sendMessage(msg);
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
    client.disconnect();
  }

}
