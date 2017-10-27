package vn.kms.launch.cleancode.Service;

import vn.kms.launch.cleancode.annotations.MaxLength;
import vn.kms.launch.cleancode.annotations.NotEmpty;
import vn.kms.launch.cleancode.annotations.NotNull;
import vn.kms.launch.cleancode.annotations.ValidSpecialCharacter;
import vn.kms.launch.cleancode.component.load.TSVFileLoader;
import vn.kms.launch.cleancode.component.validate.MaxLengthValidation;
import vn.kms.launch.cleancode.component.validate.SpecialCharacterValidation;
import vn.kms.launch.cleancode.component.validate.Validation;
import vn.kms.launch.cleancode.component.validate.ValidationFactory;
import vn.kms.launch.cleancode.module.Contact;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ContactServiceImpl implements ContactService {
  private static int yearOfReport = 2016;

  @Override
  public List<Contact> loadContactList(String url) {
    TSVFileLoader tsvFileLoader = null;
    try {
      tsvFileLoader = new TSVFileLoader();
    } catch (IOException e) {
      e.printStackTrace();
    }
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
          if (!errors.isEmpty()) {
            invalidContacts.put(contact.getId(), errors);
          } else { // populate other fields from raw fields
            contact.setAge(calculateAgeByYear(contact.getDayOfBirth())); // age
          }
        }
      }
    }
  }

  public static int calculateAgeByYear(String date_of_birth){
    String yearStr = date_of_birth.split("/")[2];
    int year = Integer.parseInt(yearStr);
    return yearOfReport - year;
  }
}
