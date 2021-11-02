package client.core;

import client.view.chat.ChatViewModel;
import client.view.friendlist.FriendListViewModel;
import client.view.login.LoginViewModel;

public class ViewModelFactory
{
  private final ModelFactory modelFactory;

  public ViewModelFactory(ModelFactory modelFactory)
  {
    this.modelFactory = modelFactory;
  }


  private LoginViewModel loginViewModel;
  public LoginViewModel getLoginViewModel()
  {
    if (loginViewModel == null)
      loginViewModel = new LoginViewModel(modelFactory.getLoginModel());
    return loginViewModel;
  }

  private ChatViewModel chatViewModel;
  public ChatViewModel getChatViewModel(){
    if (chatViewModel == null)
      chatViewModel = new ChatViewModel(modelFactory.getModel());
    return chatViewModel;
  }

  private FriendListViewModel friendListViewModel;
  public FriendListViewModel getFriendListViewModel()
  {
    if (friendListViewModel == null)
    friendListViewModel = new FriendListViewModel(modelFactory.getModel());
    return friendListViewModel;
  }
}
