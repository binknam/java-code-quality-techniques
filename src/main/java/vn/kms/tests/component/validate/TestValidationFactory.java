package vn.kms.tests.component.validate;

import org.junit.Assert;
import org.junit.Test;
import vn.kms.launch.cleancode.component.validate.*;

public class TestValidationFactory {

  private ValidationFactory validationFactory;

  public TestValidationFactory(){
    validationFactory = new ValidationFactory();
  }

  @Test
  public void getValidationShouldReturnNullWithValidationTypeNull(){
    Validation ouput = validationFactory.getValidation(null);
    Assert.assertEquals(null, ouput);
  }

  @Test
  public void getValidationShouldReturnNotEmptyClasslWithValidationTypeNotEmpty(){
    Validation ouput = validationFactory.getValidation(ValidationFactory.NOT_EMPTY_TYPE);
    Assert.assertEquals(NotEmptyValidation.class, ouput.getClass());
  }

  @Test
  public void getValidationShouldReturnNotNullClasslWithValidationTypeNotNull(){
    Validation ouput = validationFactory.getValidation(ValidationFactory.NOT_NULL_TYPE);
    Assert.assertEquals(NotNullValidation.class, ouput.getClass());
  }

  @Test
  public void getValidationShouldReturnMaxLengthClasslWithValidationTypeMaxLength(){
    Validation ouput = validationFactory.getValidation(ValidationFactory.MAX_LENGTH_TYPE);
    Assert.assertEquals(MaxLengthValidation.class, ouput.getClass());
  }

  @Test
  public void getValidationShouldReturnSpecialCharacterClasslWithValidationTypeValidSpecialCharacter(){
    Validation ouput = validationFactory.getValidation(ValidationFactory.VALID_SPECIAL_CHARACTER);
    Assert.assertEquals(SpecialCharacterValidation.class, ouput.getClass());
  }

  @Test
  public void getValidationShouldReturnEqualLengthClasslWithValidationTypeEqualLength(){
    Validation ouput = validationFactory.getValidation(ValidationFactory.EQUAL_LENGTH_TYPE);
    Assert.assertEquals(EqualLengthValidation.class, ouput.getClass());
  }

  @Test
  public void getValidationShouldReturnStateClasslWithValidationTypeValidState(){
    Validation ouput = validationFactory.getValidation(ValidationFactory.VALID_STATE);
    Assert.assertEquals(StateValidation.class, ouput.getClass());
  }

  @Test
  public void getValidationShouldReturnNullWithValidationTypeNotMatched(){
    Validation ouput = validationFactory.getValidation("Some validation type");
    Assert.assertEquals(null, ouput);
  }
}
