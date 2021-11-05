package shared;

import java.beans.PropertyChangeListener;

public interface Observable
{
  void addListener(String eventName, PropertyChangeListener listener);
  void removeListener(String eventName, PropertyChangeListener listener);
}
