package shared;

public class DeniedLoginException extends RuntimeException
{
  public DeniedLoginException(String message)
  {
    super(message);
  }
}
