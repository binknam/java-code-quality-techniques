package vn.kms.launch.cleancode.component.validate;

import vn.kms.launch.cleancode.annotations.Column;
import vn.kms.launch.cleancode.module.Contact;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

public class SpecialCharacterValidation extends Validation{

  private String validSpecialCharacter = "";

  public String getValidSpecialCharacter() {
    return validSpecialCharacter;
  }

  public void setValidSpecialCharacter(String validSpecialCharacter) {
    this.validSpecialCharacter = validSpecialCharacter;
  }

  public String getMessageValue(String columnName, Field field, Contact contact){
    String messageValue = "";
    try {
      if (columnName.equals("email")) {
        messageValue = "'" + field.get(contact).toString() + "' is invalid email format";
      }
      if (columnName.equals("zip")) {
        messageValue = "'" + field.get(contact).toString() + "' is not four or five digits";
      }
      if (columnName.equals("phone1")) {
        messageValue = "'" + field.get(contact).toString() + "' is invalid format XXX-XXX-XXXX";
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return messageValue;
  }

  @Override
  public boolean checkValidation(Contact contact, Annotation[] annotations, Field field, Map<String, String> errors, Map<String, Integer> counts) {
    Column column = null;
    for (Annotation annotation: annotations){
      if (annotation instanceof Column){
        column = (Column) annotation;
      }
    }
    try {
      if (!field.get(contact).toString().matches(validSpecialCharacter)){
        String messageValue = getMessageValue(column.name(), field, contact);
        errors.put(column.name(), messageValue);
        addFieldErrors(counts, column.name());
        return false;
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return true;
  }
}
