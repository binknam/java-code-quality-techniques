package vn.kms.launch.cleancode.component.sort;

import java.util.List;
import java.util.Map;

public interface Sorter<ObjectType> {
  public List<ObjectType> sort(List<ObjectType> objects, Map<Integer, Map<String, String>> invalidObjects , String fieldName);
}
