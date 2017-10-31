package vn.kms.tests.component.load;

import org.junit.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import vn.kms.launch.cleancode.component.load.TSVFileLoader;
import vn.kms.launch.cleancode.module.Contact;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class TestTSVFileLoader {

  private TSVFileLoader tsvFileLoader;

  private static String CONTACT_URL = "data/contacts.tsv";

  public TestTSVFileLoader() throws IOException {
    tsvFileLoader = new TSVFileLoader();
  }

  @Test
  public void loadLinesDataFromFileContactsTSVLinesCountShouldBe518(){
    String[] output = new String[0];
    try {
      output = tsvFileLoader.loadLinesDataFromFile(CONTACT_URL);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Assert.assertEquals(518, output.length);
  }

  @Test(expected = IOException.class)
  public void loadLinesDataFromFileLineWithEmptyUrlShouldThrowException() throws IOException {
    tsvFileLoader.loadLinesDataFromFile("");
  }

  @Test
  public void loadHeaderFromLine0ofContactsTSVFileShouldLengthBe14(){
    String[] lines = new String[0];
    try {
      lines = tsvFileLoader.loadLinesDataFromFile(CONTACT_URL);
    } catch (IOException e) {
      e.printStackTrace();
    }
    String[] output = tsvFileLoader.loadHeader(lines);
    Assert.assertEquals(14, output.length);
  }

  @Test(expected = NullPointerException.class)
  public void loadHeaderFromNullArrayStringShouldThrowNullPointerException(){
    String[] output = tsvFileLoader.loadHeader(null);
  }

  @Test
  public void isBlankLineShouldBeTrueWithEmptyString(){
    boolean output = tsvFileLoader.isBlankLine("");
    Assert.assertEquals(true, output);
  }

  @Test
  public void isBlankLineShouldBeFalseWithValidString(){
    boolean output = tsvFileLoader.isBlankLine("a");
    Assert.assertEquals(false, output);
  }

  @Test
  public void getDataOfLineWith3TabAndContentElementShouldLengthBe3(){
    String[] output = tsvFileLoader.getDataOfLine("a\ta\ta");
    Assert.assertEquals(3, output.length);
  }
}
