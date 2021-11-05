package shared;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;

public class MessageObject implements Serializable
{
  private final String messageText;
  private final Time messageTimeStamp;
  private final String sender;
  private ArrayList<LoginObject> chatMembers;


  public MessageObject(String msg, String sender, ArrayList<LoginObject> chatMembers)
  {
    messageText = msg;
    messageTimeStamp = new Time(System.currentTimeMillis());
    this.sender = sender;
    this.chatMembers = chatMembers;
  }


  public String getMessageText()
  {
    return "["+sender+"]:\n" + messageText;
  }

  public Time getMessageTimeStamp()
  {
    return messageTimeStamp;
  }

  public ArrayList<LoginObject> getChatMembers()
  {
    return chatMembers;
  }


  @Override public String toString()
  {
    return "MessageObject{" + "messageText='" + messageText + '\''
        + ", messageTimeStamp=" + messageTimeStamp + ", sender='" + sender
        + '\'' + ", chatMembers=" + chatMembers + '}';
  }
}
