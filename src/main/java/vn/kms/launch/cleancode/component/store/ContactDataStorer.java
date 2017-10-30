package vn.kms.launch.cleancode.component.store;

import vn.kms.launch.cleancode.component.load.TSVFileLoader;
import vn.kms.launch.cleancode.module.Contact;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class ContactDataStorer implements DataStorer<Contact> {
  @Override
  public void storeData(List<Contact> allContacts, Map<Integer, Map<String, String>> invalidContacts, String outputFileName, String[] header) throws IOException {
    File outputFile = new File("output");
    if (!outputFile.exists()) {
      outputFile.mkdirs();
    }
    Writer writer = new FileWriter(new File(outputFile, outputFileName));

    for (int i = 0 ; i < header.length - 1; i++){
      writer.write(header[i] + "\t");
    }
    writer.write(header[header.length - 1] + "\r\n");

    for (Contact contact : allContacts) {
      if (!invalidContacts.containsKey(contact.getId())) {
        try {
        Field[] fields = Contact.class.getDeclaredFields();
        for (int i = 0 ; i < fields.length - 1; i++){
          fields[i].setAccessible(true);
          writer.write(fields[i].get(contact).toString() + "\t");
        }
        fields[fields.length - 1].setAccessible(true);
        writer.write(fields[fields.length - 1].get(contact).toString() + "\r\n");
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    writer.flush();
  }
}
