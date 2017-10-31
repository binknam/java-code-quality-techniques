package vn.kms.tests.component.validate;

import org.junit.Assert;
import org.junit.Test;
import vn.kms.launch.cleancode.component.validate.Validation;
import vn.kms.launch.cleancode.component.validate.ValidationFactory;
import vn.kms.launch.cleancode.module.Contact;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TestValidation {
  private Validation validation;
  private ValidationFactory validationFactory;

  public TestValidation(){
    validationFactory = new ValidationFactory();
  }

  @Test
  public void addFieldErrorsShouldPutKeyTestToValueCount1(){
    Map<String, Integer> counts = new HashMap<>();
    validation = new Validation() {
      @Override
      public boolean checkValidation(Contact contact, Annotation[] annotations, Field field, Map<String, String> errors, Map<String, Integer> counts) {
        return false;
      }
    };
    validation.addFieldErrors(counts, "Test");
    Assert.assertEquals(1, counts.values().toArray()[0]);
  }
}
