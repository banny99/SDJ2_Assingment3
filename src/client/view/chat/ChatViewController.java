package client.view.chat;

import client.core.ViewHandler;
import client.view.ViewController;
import client.view.friendlist.FriendListViewModel;
import client.view.login.LoginViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import shared.LoginObject;
import shared.MessageObject;
import java.sql.Time;
import java.util.ArrayList;

public class ChatViewController implements ViewController
{

  @FXML private ChoiceBox<LoginObject> activeMembersChoiceBox;
  @FXML private TableView<MessageObject> chatTable;
  @FXML private TableColumn<MessageObject, String> msgTC;
  @FXML private TableColumn<MessageObject, Time> timeTC;
  @FXML private TextArea chatTextField;
  @FXML private Label messageTo_label;
  private final String messageTo_defaultLabelText = "(to: everyone)";

  private ViewHandler viewHandler;
  private ChatViewModel chatViewModel;
  private LoginObject loginObject;

  private ArrayList<LoginObject> messageTo_list;


  @Override public void init(ViewHandler vh, ChatViewModel cvm, LoginObject lo)
  {
    viewHandler = vh;
    chatViewModel = cvm;
    loginObject = lo;
    messageTo_list = new ArrayList<>();

    //binding
    msgTC.setText(loginObject.getUsername());
    msgTC.setCellValueFactory(new PropertyValueFactory<>("messageText"));
    timeTC.setCellValueFactory(new PropertyValueFactory<>("messageTimeStamp"));
    chatTable.setItems(chatViewModel.getTableContentProperty());

    //choiceBox - string converter
    activeMembersChoiceBox.setConverter(new StringConverter<>()
    {
      @Override public String toString(LoginObject loginObject)
      {
        return loginObject.getUsername();
      }

      @Override public LoginObject fromString(String s)
      {
        return activeMembersChoiceBox.getItems().stream().filter(ap ->
            ap.getUsername().equals(s)).findFirst().orElse(null);
      }
    });
    //on-select action
    activeMembersChoiceBox.setOnAction(this::messageTo_selection);
    activeMembersChoiceBox.setItems(chatViewModel.getActiveMembersProperty());

    chatViewModel.requestConnections();
  }

  private void messageTo_selection(ActionEvent actionEvent)
  {
    LoginObject selectedItem = activeMembersChoiceBox.getSelectionModel().getSelectedItem();
    if (selectedItem!=null && !messageTo_list.contains(selectedItem))
    {
      messageTo_list.add(selectedItem);

      StringBuilder msgTo = new StringBuilder("(to: ");
      for (LoginObject l : messageTo_list) {
        msgTo.append(l.getUsername()).append(",");}
      msgTo.append(")");
      messageTo_label.setText(msgTo.toString());

      activeMembersChoiceBox.getSelectionModel().clearSelection();
    }
  }


  public void backButtonPressed(ActionEvent actionEvent)
  {
    viewHandler.openFriendListView();
  }

  public void sendMsgButtonPressed(ActionEvent actionEvent)
  {
    String msg = chatTextField.getText();
    String sender = loginObject.getUsername();

    if (activeMembersChoiceBox.getItems().containsAll(messageTo_list)){
      MessageObject messageObject = new MessageObject(msg, sender, messageTo_list);
      chatViewModel.sendMessage(messageObject);
    }

    chatTextField.clear();
    messageTo_label.setText(messageTo_defaultLabelText);
    messageTo_list.clear();
    activeMembersChoiceBox.getSelectionModel().clearSelection();
  }

  public void clearButtonPressed(ActionEvent actionEvent)
  {
    messageTo_list.clear();
    activeMembersChoiceBox.getSelectionModel().clearSelection();
    messageTo_label.setText(messageTo_defaultLabelText);
  }


  @Override public void init(ViewHandler vh, LoginViewModel lvm)
  {
    //nothing
  }
  @Override public void init(ViewHandler vh, FriendListViewModel fvm, LoginObject loggedUser)
  {
    //nothing
  }

  @Override public void closeWindow()
  {
    chatViewModel.disconnect();
  }
}
