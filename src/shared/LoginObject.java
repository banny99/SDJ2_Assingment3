package shared;

import java.io.Serializable;
import java.sql.Time;

public class LoginObject implements Serializable
{
  private final String username;
  private final String password;
  private final Time timeStamp;

  public LoginObject(String username, String password)
  {
    this.username = username;
    this.password = password;
    timeStamp = new Time(System.currentTimeMillis());
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public Time getTimeStamp()
  {
    return timeStamp;
  }

  public boolean equals(Object o)
  {
    if (!(o instanceof LoginObject other))
      return false;

    return this.username.equals(other.username)
        && this.password.equals(other.password);
  }

  @Override public String toString()
  {
    return "LoginObject{" + "username='" + username + '\'' + ", password='"
        + password + '\'' + '}';
  }
}
