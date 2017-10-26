package vn.kms.launch.cleancode;

import vn.kms.launch.cleancode.component.load.TSVFileLoader;
import vn.kms.launch.cleancode.module.Contact;

import java.util.List;

public class Application {
  public static void main(String[] args) throws Exception {
    TSVFileLoader tsvFileLoader = new TSVFileLoader();
    List<Contact> contactList = tsvFileLoader.loadData("data/contacts.tsv");
    System.out.println(contactList.get(3).getFirstName());
  }
}
