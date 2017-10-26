package vn.kms.launch.cleancode.component.map;

import vn.kms.launch.cleancode.annotations.Column;
import vn.kms.launch.cleancode.module.Contact;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TSVFileMapOrders implements FileMapOrders {

  private String[] fieldTitles;

  public TSVFileMapOrders(String[] fieldTitles){
    this.fieldTitles = fieldTitles;
  }

  public Map<String, Integer> getContactFieldMapOrders(){
    Map<String, Integer> mapOrders = new HashMap<String, Integer>();
    for (int i = 0; i < fieldTitles.length; i++){
      mapOrders.put(fieldTitles[i], i);
    }
    return mapOrders;
  }

   public List<Integer> getColumnOrders(){
    List<Integer> orders = new ArrayList<Integer>();
    Field[] fields = Contact.class.getDeclaredFields();
    for (Field field: fields){
      Annotation[] annotations = field.getDeclaredAnnotations();
      for(Annotation annotation: annotations){
        if (annotation instanceof Column) {
          Column column = (Column) annotation;
          orders.add(getContactFieldMapOrders().get(column.name()));
        }
      }
    }
    return orders;
  }

  @Override
  public Contact getMapedData(Object[] data) {
    List<Integer> fileOrders = getColumnOrders();
    Contact contact = new Contact();
    Field[] fields = Contact.class.getDeclaredFields();
    try {
      for (int i = 0; i < fields.length; i++){
        if (fields[i].getDeclaredAnnotations().length != 0) {
          fields[i].setAccessible(true);
          if (fieldTitles[fileOrders.get(i)].equals("id")) {
            fields[i].set(contact, Integer.parseInt(data[fileOrders.get(i)].toString()));
          } else {
            fields[i].set(contact, data[fileOrders.get(i)].toString());
          }
        }
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return contact;
  }
}
