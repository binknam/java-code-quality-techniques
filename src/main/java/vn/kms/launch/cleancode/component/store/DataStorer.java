package vn.kms.launch.cleancode.component.store;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DataStorer<ObjectType> {
  public void storeData(List<ObjectType> allObjects, Map<Integer, Map<String, String>> invalidObjects, String outputFileName, String[] header) throws IOException;
}
