package client.view.chat;

import client.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.LoginObject;
import shared.MessageObject;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

public class ChatViewModel
{
  private final Model chatModelManager;
  private final ObservableList<MessageObject> tableContentProperty;
  private final ObservableList<LoginObject> activeMembersProperty;

  public ChatViewModel(Model cmm)
  {
    chatModelManager = cmm;
    tableContentProperty = FXCollections.observableArrayList();
    activeMembersProperty = FXCollections.observableArrayList();

    //subscription
    chatModelManager.addListener("msg", this::receiveMsg);
    chatModelManager.addListener("cnct", this::updateActiveMembers);
  }


  public void requestConnections()
  {
    chatModelManager.requestConnections();
  }

  private void updateActiveMembers(PropertyChangeEvent evt)
  {
    activeMembersProperty.setAll((ArrayList<LoginObject>) evt.getNewValue());
  }


  public void sendMessage(MessageObject msg)
  {
    chatModelManager.processMessage(msg);
  }

  private void receiveMsg(PropertyChangeEvent evt)
  {
    tableContentProperty.addAll((MessageObject) evt.getNewValue());
  }


  public ObservableList<MessageObject> getTableContentProperty()
  {
    return tableContentProperty;
  }

  public ObservableList<LoginObject> getActiveMembersProperty()
  {
    return activeMembersProperty;
  }


  public void disconnect()
  {
    chatModelManager.disconnect();
  }

}
