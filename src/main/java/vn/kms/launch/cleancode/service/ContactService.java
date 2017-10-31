package vn.kms.launch.cleancode.service;

import vn.kms.launch.cleancode.module.Contact;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ContactService {
  public List<Contact> loadContactList(String url) throws IllegalAccessException;

  public void checkValidation(List<Contact> contacts, Map<Integer, Map<String, String>> errors, Map<String, Integer> counts);

  public List<Contact> sortValidContacts(List<Contact> allContacts, Map<Integer, Map<String, String>> invalidContacts, String fieldName);

  public void storeContactData(List<Contact> allContact, Map<Integer, Map<String, String>> invalidContacts, String outputFileName, String[] header);

  public Map reportContactData(List<Contact> allContact, Map<Integer, Map<String, String>> invalidContacts);

  public void storeRedport(Map reports,  Map<String, Integer> counts,  Map<Integer, Map<String, String>> invalidContacts) throws IOException;

}
