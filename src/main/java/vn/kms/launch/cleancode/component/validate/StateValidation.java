package vn.kms.launch.cleancode.component.validate;

import vn.kms.launch.cleancode.module.Contact;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StateValidation extends Validation {
  public static final Set<String> m_valid_state_codes = new HashSet<>(Arrays.asList("AL","AK","AS","AZ","AR","CA","CO",
                                                                                      "CT","DE","DC","FL","GA","GU","HI","ID",
                                                                                      "IL","IN","IA","KS","KY","LA","ME","MD",
                                                                                      "MH","MA","MI","FM","MN","MS","MO", "MT",
                                                                                      "NE","NV","NH","NJ","NM","NY","NC","ND",
                                                                                      "MP","OH","OK","OR","PW","PA","PR","RI",
                                                                                      "SC","SD","TN","TX","UT","VT", "VA","VI",
                                                                                      "WA","WV","WI","WY"));
  @Override
  public boolean checkValidation(Contact contact, Annotation[] annotations, Field field, Map<String, String> errors, Map<String, Integer> counts) {
    try {
      if (!m_valid_state_codes.contains(field.get(contact).toString())) {
        errors.put("state", "'" + field.get(contact).toString() + "' is incorrect state code");
        addFieldErrors(counts, "state");
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return true;
  }
}
