package vn.kms.launch.cleancode.Service;

import vn.kms.launch.cleancode.module.Contact;

import java.util.List;
import java.util.Map;

public interface ContactService {
  public List<Contact> loadContactList(String url);

  public void checkValidation(List<Contact> contacts, Map<Integer, Map<String, String>> errors, Map<String, Integer> counts);
}
