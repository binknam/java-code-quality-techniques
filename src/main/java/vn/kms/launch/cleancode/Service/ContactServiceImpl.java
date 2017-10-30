package vn.kms.launch.cleancode.Service;

import vn.kms.launch.cleancode.annotations.*;
import vn.kms.launch.cleancode.component.load.TSVFileLoader;
import vn.kms.launch.cleancode.component.rerport.ContactReporter;
import vn.kms.launch.cleancode.component.rerport.Reporter;
import vn.kms.launch.cleancode.component.sort.ContactSorter;
import vn.kms.launch.cleancode.component.sort.Sorter;
import vn.kms.launch.cleancode.component.store.ContactDataStorer;
import vn.kms.launch.cleancode.component.store.DataStorer;
import vn.kms.launch.cleancode.component.validate.*;
import vn.kms.launch.cleancode.module.Contact;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ContactServiceImpl implements ContactService {
  private static int yearOfReport = 2016;

  private TSVFileLoader tsvFileLoader = null;

  public TSVFileLoader getTsvFileLoader() {
    return tsvFileLoader;
  }

  public void setTsvFileLoader(TSVFileLoader tsvFileLoader) {
    this.tsvFileLoader = tsvFileLoader;
  }

  public ContactServiceImpl(){
    try {
      tsvFileLoader = new TSVFileLoader();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Contact> loadContactList(String url) {
    List<Contact> contactList = tsvFileLoader.loadData(url);
    return contactList;
  }

  @Override
  public void checkValidation(List<Contact> contacts, Map<Integer, Map<String, String>> invalidContacts, Map<String, Integer> counts) {
    Field[] fields = Contact.class.getDeclaredFields();
    ValidationFactory validationFactory = new ValidationFactory();
    for (Contact contact: contacts){
      for (Field field: fields){
        field.setAccessible(true);
        Annotation[] annotations = field.getDeclaredAnnotations();
        for (Annotation annotation: annotations){
          Validation validation;
          Map<String, String> errors = new TreeMap<String, String>();
          if (annotation instanceof NotEmpty){
            validation = validationFactory.getValidation(ValidationFactory.NOT_EMPTY_TYPE);
            validation.checkValidation(contact, annotations, field, errors, counts);
          }
          if (annotation instanceof NotNull) {
            validation = validationFactory.getValidation(ValidationFactory.NOT_NULL_TYPE);
            validation.checkValidation(contact, annotations, field, errors, counts);
          }
          if (annotation instanceof MaxLength){
            validation = validationFactory.getValidation(ValidationFactory.MAX_LENGTH_TYPE);
            ((MaxLengthValidation) validation).setMaxLength(((MaxLength)annotation).value());
            validation.checkValidation(contact, annotations, field, errors, counts);
          }
          if (annotation instanceof ValidSpecialCharacter){
            validation = validationFactory.getValidation(ValidationFactory.VALID_SPECIAL_CHARACTER);
            ((SpecialCharacterValidation) validation).setValidSpecialCharacter(((ValidSpecialCharacter)annotation).value());
            validation.checkValidation(contact, annotations, field, errors, counts);
          }
          if (annotation instanceof EqualLength){
            validation = validationFactory.getValidation(ValidationFactory.EQUAL_LENGTH_TYPE);
            ((EqualLengthValidation) validation).setEqualLength(((EqualLength)annotation).value());
            validation.checkValidation(contact, annotations, field, errors, counts);
          }
          if (annotation instanceof ValidState){
            validation = validationFactory.getValidation(ValidationFactory.VALID_STATE);
            validation.checkValidation(contact, annotations, field, errors, counts);
          }
          if (!errors.isEmpty()) {
            invalidContacts.put(contact.getId(), errors);
          } else { // populate other fields from raw fields
            contact.setAge(calculateAgeByYear(contact.getDayOfBirth())); // age
          }
        }
      }
    }
  }

  @Override
  public List<Contact> sortValidContacts(List<Contact> allContacts, Map<Integer, Map<String, String>> invalidContacts, String fieldName) {
    Sorter sorter = new ContactSorter();
    return sorter.sort(allContacts, invalidContacts, fieldName);
  }

  @Override
  public void storeContactData(List<Contact> allContact, Map<Integer, Map<String, String>> invalidContacts, String outputFileName, String[] header) {
    DataStorer dataStorer = new ContactDataStorer();
    try {
      dataStorer.storeData(allContact, invalidContacts, outputFileName, header);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Map reportContactData(List<Contact> allContact, Map<Integer, Map<String, String>> invalidContacts) {
    Reporter reporter = new ContactReporter();
    Map report = reporter.report(allContact, invalidContacts);
    return report;
  }

  public static int calculateAgeByYear(String date_of_birth){
    String yearStr = date_of_birth.split("/")[2];
    int year = Integer.parseInt(yearStr);
    return yearOfReport - year;
  }
}
