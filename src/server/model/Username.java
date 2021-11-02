package server.model;

public class Username
{
  private String name;

  public Username(String username)
  {
    if (username == null || username.length() < 3)
    {
      throw new IllegalArgumentException("Username must have at least 3 characters");
    }
    this.name = username;
  }

  public String getName()
  {
    return name;
  }

  @Override public String toString()
  {
    return name;
  }
}
