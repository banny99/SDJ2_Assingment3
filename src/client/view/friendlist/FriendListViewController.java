package client.view.friendlist;

import client.core.ViewHandler;
import client.view.ViewController;
import client.view.chat.ChatViewModel;
import client.view.login.LoginViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import shared.LoginObject;

public class FriendListViewController implements ViewController
{

  @FXML private Label loggedUserLabel;
  @FXML private TableView<LoginObject> activeConnectionsTab;
  @FXML private TableColumn<LoginObject, String> onlineFriendsCol;
  @FXML private TableColumn<LoginObject, String> timeCol;

  private ViewHandler viewHandler;
  private FriendListViewModel friendListViewModel;
  private LoginObject loggedUser;


  @Override public void init(ViewHandler vh, FriendListViewModel fvm, LoginObject loggedUser)
  {
    viewHandler = vh;
    friendListViewModel = fvm;
    this.loggedUser = loggedUser;

    onlineFriendsCol.setCellValueFactory(new PropertyValueFactory<>("username"));
    timeCol.setCellValueFactory(new PropertyValueFactory<>("timeStamp"));
    activeConnectionsTab.setItems(friendListViewModel.getConnectionsProperty());

    loggedUserLabel.setText("(logged-in as: " + loggedUser.getUsername() + ")");

    friendListViewModel.requestConnections();
  }


  public void openChatButtonPressed(ActionEvent actionEvent)
  {
    viewHandler.openChatView();
  }


  @Override public void init(ViewHandler vh, LoginViewModel lvm)
  {
    //nothing
  }
  @Override public void init(ViewHandler vh, ChatViewModel cvm, LoginObject loginObject)
  {
    //nothing
  }


  @Override public void closeWindow()
  {
    friendListViewModel.disconnect();
  }
}
