package view;

import core.ViewHandler;
import view.chat.ChatViewModel;
import view.friendlist.FriendListViewModel;
import view.login.LoginViewModel;
import shared.LoginObject;

public interface ViewController
{
  void init(ViewHandler vh, LoginViewModel lvm);

  void init(ViewHandler vh, ChatViewModel cvm, LoginObject loginObject);

  void init(ViewHandler vh, FriendListViewModel fvm, LoginObject loggedUser);

  void closeWindow();

}
