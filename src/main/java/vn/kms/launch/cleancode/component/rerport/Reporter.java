package vn.kms.launch.cleancode.component.rerport;

import java.util.List;
import java.util.Map;

public interface Reporter<ObjectType> {
  public Map report(List<ObjectType> allObjects, Map<Integer, Map<String, String>> invalidObjects);
}
