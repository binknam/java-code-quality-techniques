package vn.kms.launch.cleancode.component.map;

import java.util.List;

public interface FileMapOrders<T, R> {
  T getMapedData(R[] data);
}
