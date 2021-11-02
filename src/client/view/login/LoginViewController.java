package client.view.login;

import client.core.ViewHandler;
import client.view.chat.ChatViewModel;
import client.view.ViewController;
import client.view.friendlist.FriendListViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import shared.DeniedLoginException;
import shared.LoginObject;

public class LoginViewController implements ViewController
{
  @FXML private Label messageLabel;
  @FXML private TextField tf_username;
  @FXML private TextField tf_password;

  private ViewHandler viewHandler;
  private LoginViewModel loginViewModel;


  @Override public void init(ViewHandler vh, LoginViewModel lvm)
  {
    viewHandler = vh;
    loginViewModel = lvm;
  }


  public void onLoginBtnPressed(ActionEvent actionEvent)
  {
    LoginObject loginObject = new LoginObject(tf_username.getText(), tf_password.getText());
    try
    {
      loginViewModel.loginBtnPressed(loginObject);
      System.out.println("logged in");

//      viewHandler.openFriendListView(loginObject);
      viewHandler.afterLoginApprovedAction(loginObject);
    }
    catch (DeniedLoginException e)
    {
      String message = e.getMessage();
      String labelText = "";
      for (int i=0; i<message.length(); i++){
        if (i>0 && i%53 == 0)
          labelText += "\n";
        labelText += message.charAt(i);
      }

      messageLabel.setVisible(true);
      messageLabel.setText("! " + labelText);
    }
  }


  @Override public void init(ViewHandler vh, ChatViewModel cvm,
      LoginObject loginObject)
  {
    //nothing
  }
  @Override public void init(ViewHandler vh, FriendListViewModel fvm, LoginObject loggedUser)
  {
    //nothing
  }

  @Override public void closeWindow()
  {
    loginViewModel.disconnect();
  }
}
