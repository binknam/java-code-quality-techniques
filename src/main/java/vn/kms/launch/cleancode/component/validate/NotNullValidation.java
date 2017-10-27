package vn.kms.launch.cleancode.component.validate;

import vn.kms.launch.cleancode.annotations.Column;
import vn.kms.launch.cleancode.module.Contact;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

public class NotNullValidation extends Validation {
  @Override
  public boolean checkValidation(Contact contact, Annotation[] annotations, Field field, Map errors, Map counts) {
    Column column = null;
    for (Annotation annotation: annotations){
      if (annotation instanceof Column){
        column = (Column) annotation;
      }
    }
    try {
      if (field.get(contact) != null){
        errors.put(column.name(), " is null");
        addFieldErrors(counts, column.name());
        return false;
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return true;
  }
}
