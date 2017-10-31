package vn.kms.launch.cleancode.service;

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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ContactServiceImpl implements ContactService {
  private static int yearOfReport = 2016;

  private static final Map<String, String> report_headers = new HashMap<String, String>(){{
    put("invalid-contact-details","contact_id\terror_field\terror_message\r\n");
    put("invalid-contact-summary","field_name\tnumber_of_invalid_contact\r\n");
    put("contact-per-state","state_code\tnumber_of_contact\r\n");
    put("contact-per-age-group","group\tnumber_of_contact\tpercentage_of_contact\r\n");
  }};

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
  public List<Contact> loadContactList(String url) throws IllegalAccessException {
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

  @Override
  public void storeRedport(Map reports, Map<String, Integer> counts,  Map<Integer, Map<String, String>> invalidContacts) {
    Writer writer = null;
    File outputFile = null;
    for(Object object:report_headers.keySet()) {
      try {
      String reportName = (String) object;
      outputFile = new File("output");
      if (!outputFile.exists()) {
        outputFile.mkdirs();
      }

      writer = new FileWriter(new File(outputFile, reportName + ".tab"));
      writer.write(report_headers.get(reportName));

      Map<String, Integer> report = (Map<String, Integer>) reports.get(reportName);
      if (reportName.equals("contact-per-age-group")) {
        int total = 0;
        for (Integer v : report.values()) {
          total += v;
        }
        String[] age_groups = {"Children", "Adolescent", "Adult", "Middle Age", "Senior"};
        for (String item : age_groups) {
          writer.write(item + "\t" + report.get(item) + "\t" + (int) ((report.get(item) * 100.0f) / total) + "%\r\n");
        }
      } else if (reportName.equals("contact-per-state")) {
        report = new TreeMap<>(report);
        for (String item : report.keySet()) {
          writer.write(item + "\t" + report.get(item) + "\r\n");
        }
      } else if (reportName.equals("invalid-contact-summary")) {
        for (Map.Entry entry : counts.entrySet()) {
          writer.write(entry.getKey() + "\t" + entry.getValue() + "\r\n");
        }
      } else {
        for (Map.Entry<Integer, Map<String, String>> entry : invalidContacts.entrySet()) {
          int contactID = entry.getKey();
          Map<String, String> error_by_fields = entry.getValue();
          for (String field_name : error_by_fields.keySet()) {
            writer.write(contactID + "\t" + field_name + "\t" + error_by_fields.get(field_name) + "\r\n");
          }
        }
      }

      writer.flush();
      System.out.println("Generated report " + "output/" + reportName + ".tab");
      } catch (IOException e) {
        e.printStackTrace();
      } finally {

      }
    }
  }

  public static int calculateAgeByYear(String date_of_birth){
    String yearStr = date_of_birth.split("/")[2];
    int year = Integer.parseInt(yearStr);
    return yearOfReport - year;
  }
}
