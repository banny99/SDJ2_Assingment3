package shared;

public class TransferObject
{
  private final String type;
  private final String nestedObjectJson;

  public TransferObject(String type, String content)
  {
    this.type = type;
    this.nestedObjectJson = content;
  }

  public String getType()
  {
    return type;
  }

  public String getNestedObjectJson()
  {
    return nestedObjectJson;
  }

  @Override public String toString()
  {
    return "TransferObject{" + "type='" + type + '\'' + ", contentClass='"
        + nestedObjectJson + '\'' + '}';
  }
}
