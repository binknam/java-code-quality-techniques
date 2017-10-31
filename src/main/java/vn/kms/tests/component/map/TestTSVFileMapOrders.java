package vn.kms.tests.component.map;

import org.junit.Assert;
import org.junit.Test;
import vn.kms.launch.cleancode.component.load.TSVFileLoader;
import vn.kms.launch.cleancode.component.map.TSVFileMapOrders;
import vn.kms.launch.cleancode.module.Contact;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TestTSVFileMapOrders {

  private static String CONTACT_URL = "data/contacts.tsv";

  private TSVFileLoader tsvFileLoader;
  private TSVFileMapOrders tsvFileMapOrders;
  String[] lines;

  public TestTSVFileMapOrders() throws IOException {
    tsvFileLoader = new TSVFileLoader();
    lines = tsvFileLoader.loadLinesDataFromFile(CONTACT_URL);

    tsvFileMapOrders = new TSVFileMapOrders(tsvFileLoader.loadHeader(lines));
  }

  @Test
  public void getContactFileMapOrdersShouldReturnMapOrdersSize14(){
    Map<String, Integer> mapOrders = tsvFileMapOrders.getContactFieldMapOrders();
    Assert.assertEquals(14, mapOrders.size());
  }

  @Test
  public void getColumnOrdersShouldReturnOrdersSize10(){
    List<Integer> orders = tsvFileMapOrders.getColumnOrders();
    Assert.assertEquals(10, orders.size());
  }
}
