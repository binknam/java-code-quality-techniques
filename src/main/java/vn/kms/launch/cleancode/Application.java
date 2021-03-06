package vn.kms.launch.cleancode;

import vn.kms.launch.cleancode.Service.ContactService;
import vn.kms.launch.cleancode.Service.ContactServiceImpl;
import vn.kms.launch.cleancode.annotations.NotEmpty;
import vn.kms.launch.cleancode.component.load.TSVFileLoader;
import vn.kms.launch.cleancode.component.sort.ContactSorter;
import vn.kms.launch.cleancode.component.validate.Validation;
import vn.kms.launch.cleancode.component.validate.ValidationFactory;
import vn.kms.launch.cleancode.module.Contact;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class Application {

  public static ContactServiceImpl contactService = new ContactServiceImpl();

  public static void main(String[] args) throws Exception {

    Map<Integer, Map<String, String>> invalidContacts = new LinkedHashMap<>(); // invalidContacts order by ID
    Map<String, Integer> fieldErorrCounts = new HashMap<String, Integer>();

    List<Contact> contactList = contactService.loadContactList("data/contacts.tsv");

    contactService.checkValidation(contactList, invalidContacts, fieldErorrCounts);

    List<Contact> sortedContacts = contactService.sortValidContacts(contactList, invalidContacts, ContactSorter.DATE_OF_BIRTH);
    sortedContacts.get(0);

    contactService.storeContactData(contactList, invalidContacts, "valid-contacts.tab", contactService.getTsvFileLoader().getHeader());

    Map reports = contactService.reportContactData(contactList, invalidContacts);

    contactService.storeRedport(reports, fieldErorrCounts, invalidContacts);
    String a = "";
  }

}
