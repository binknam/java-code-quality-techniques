package vn.kms.launch.cleancode.component.validate;

import vn.kms.launch.cleancode.annotations.Column;
import vn.kms.launch.cleancode.module.Contact;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

public abstract class Validation {
  protected Field[] contactFields = Contact.class.getDeclaredFields();

  public abstract boolean checkValidation(Contact contact, Annotation[] annotations, Field field, Map<String, String> errors, Map<String, Integer> counts);

  protected void addFieldErrors(Map<String, Integer> counts, String fieldName) {
    Integer count = counts.get(fieldName);
    if (count == null) {
      count = new Integer(0);
    }
    count = count + 1;
    counts.put(fieldName, count);
  }
}
