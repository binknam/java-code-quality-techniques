package vn.kms.launch.cleancode.component.validate;

import vn.kms.launch.cleancode.module.Contact;

import java.lang.reflect.Field;

public class ValidationFactory {

  public static String NOT_EMPTY_TYPE = "Not empty";
  public static String NOT_NULL_TYPE = "Not null";
  public static String MAX_LENGTH_TYPE = "Max length value";
  public static String VALID_SPECIAL_CHARACTER = "Valid special character";
  public static String EQUAL_LENGTH_TYPE = "Equal length";
  public static String VALID_STATE = "Valid state";


  Field[] fields = Contact.class.getDeclaredFields();

  public Validation getValidation(String validationType){
    if (validationType == null){
      return null;
    }
    if (validationType.equals(NOT_EMPTY_TYPE)){
      return new NotEmptyValidation();
    }
    if (validationType.equals(NOT_NULL_TYPE)){
      return new NotNullValidation();
    }
    if (validationType.equals(MAX_LENGTH_TYPE)){
      return new MaxLengthValidation();
    }
    if (validationType.equals(VALID_SPECIAL_CHARACTER)){
      return new SpecialCharacterValidation();
    }
    if (validationType.equals(EQUAL_LENGTH_TYPE)){
      return new EqualLengthValidation();
    }
    if (validationType.equals(VALID_STATE)){
      return new StateValidation();
    }
    return null;
  }
}
