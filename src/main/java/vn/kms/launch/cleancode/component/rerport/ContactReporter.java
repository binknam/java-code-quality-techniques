package vn.kms.launch.cleancode.component.rerport;



import vn.kms.launch.cleancode.module.Contact;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactReporter implements Reporter<Contact> {
  @Override
  public Map report(List<Contact> allContacts, Map<Integer, Map<String, String>> invalidContacts) {
    Map reports = new HashMap<>();

    Map<String, Integer> mapContactPerState = reportContactPerState(allContacts, invalidContacts);
    Map<String, Integer> mapContactPerAge = reportContactPerAge(allContacts, invalidContacts);

    reports.put("contact-per-state", mapContactPerState);
    reports.put("contact-per-age-group", mapContactPerAge);
    return reports;
  }

  Map<String, Integer> reportContactPerState(List<Contact> allContacts, Map<Integer, Map<String, String>> invalidContacts){
    Map<String, Integer> mapContactPerState = new HashMap<>();
    for (Contact contact : allContacts) {
      if (!invalidContacts.containsKey(contact.getId())) {
        int state_count = 0;
        if(mapContactPerState.containsKey(contact.getState())) {
          state_count = mapContactPerState.get(contact.getState());
        }
        mapContactPerState.put(contact.getState(), state_count + 1);
      }
    }
    return mapContactPerState;
  }

  Map<String, Integer> reportContactPerAge(List<Contact> allContacts, Map<Integer, Map<String, String>> invalidContacts){
    Map<String, Integer> mapContactPerAge = new HashMap<>();
    for (Contact contact : allContacts) {
      if (!invalidContacts.containsKey(contact.getId())) {
        int state_count = 0;
        int age_group_count = 0;
        if(mapContactPerAge.containsKey(calculateAgeGroup(contact.getAge()))) {
          age_group_count = mapContactPerAge.get(calculateAgeGroup(contact.getAge()));
        }
        mapContactPerAge.put(calculateAgeGroup(contact.getAge()), age_group_count + 1);
      }
    }
    return mapContactPerAge;
  }

  private String calculateAgeGroup(int age){
    if(age<=9){
      return "Children";
    } else if(age<19) {
      return "Adolescent";
    } else if(age<=45) {
      return "Adult";
    } else if(age<=60) {
      return "Middle Age";
    } else {
      return "Senior";
    }
  }
}
