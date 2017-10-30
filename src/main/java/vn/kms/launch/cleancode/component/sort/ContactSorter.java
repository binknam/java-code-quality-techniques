package vn.kms.launch.cleancode.component.sort;

import vn.kms.launch.cleancode.module.Contact;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ContactSorter implements Sorter<Contact> {
  public static String ID = "id";
  public static String FIRST_NAME = "firstName";
  public static String LAST_NAME = "lastName";
  public static String ADDRESS = "address";
  public static String CITY = "city";
  public static String STATE = "state";
  public static String ZIP_CODE = "zipCode";
  public static String MOBILE_PHONE = "mobilePhone";
  public static String EMAIL = "email";
  public static String DATE_OF_BIRTH = "dateOfBirth";

  @Override
  public List<Contact> sort(List<Contact> allContacts, Map<Integer, Map<String, String>> invalidContacts, String fieldName) {
    for (int i = 0; i < allContacts.size(); i++) {
      for (int j = allContacts.size() - 1; j > i; j--) {
        Contact contact_a = allContacts.get(i);
        Contact contact_b = allContacts.get(j);
        if (!invalidContacts.containsKey(contact_a.getId()) && !invalidContacts.containsKey(contact_b.getId())) {
          try {
            Field contactField = Contact.class.getDeclaredField(fieldName);
            contactField.setAccessible(true);
            if (fieldName == ID || fieldName == ZIP_CODE) {
              int numberValue1 = Integer.parseInt(contactField.get(contact_a).toString());
              int numberValue2 = Integer.parseInt(contactField.get(contact_b).toString());
              if (numberValue1 > numberValue2) {
                allContacts.set(i, contact_b);
                allContacts.set(j, contact_a);
              }
            } else
              if (fieldName == DATE_OF_BIRTH){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm/dd/yyyy");
                String date1 = contactField.get(contact_a).toString();
                String date2 = contactField.get(contact_b).toString();
                Date dateValue1 = simpleDateFormat.parse(date1);
                Date dateValue2 = simpleDateFormat.parse(date2);
                if (dateValue1.compareTo(dateValue2) > 0){
                  allContacts.set(i, contact_b);
                  allContacts.set(j, contact_a);
                }
              }
              else{
                String value1 = contactField.get(contact_a).toString();
                String value2 = contactField.get(contact_b).toString();
                if (value1.compareTo(value2) > 0){
                  allContacts.set(i, contact_b);
                  allContacts.set(j, contact_a);
                }
              }
          } catch (NoSuchFieldException e) {
            e.printStackTrace();
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          } catch (ParseException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return allContacts;
  }
}
