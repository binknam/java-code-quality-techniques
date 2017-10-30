package vn.kms.launch.cleancode.component.validate;

import vn.kms.launch.cleancode.annotations.Column;
import vn.kms.launch.cleancode.module.Contact;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

public class EqualLengthValidation extends Validation {
  private int equalLength = -1;

  @Override
  public boolean checkValidation(Contact contact, Annotation[] annotations, Field field, Map<String, String> errors, Map<String, Integer> counts) {
    Column column = null;
    for (Annotation annotation: annotations){
      if (annotation instanceof Column){
        column = (Column) annotation;
      }
    }
    try {
      if (field.get(contact).toString().trim().length() != equalLength){
        errors.put(column.name(), "'" + field.get(contact).toString() + "''s length is over " + equalLength);
        addFieldErrors(counts, column.name());
        return false;
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return true;
  }

  public int getEqualLength() {
    return equalLength;
  }

  public void setEqualLength(int equalLength) {
    this.equalLength = equalLength;
  }
}
