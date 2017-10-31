package vn.kms.launch.cleancode.component.load;

import vn.kms.launch.cleancode.annotations.Column;
import vn.kms.launch.cleancode.component.map.TSVFileMapOrders;
import vn.kms.launch.cleancode.module.Contact;


import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class TSVFileLoader implements FileLoader {

    private String[] header = null;

    public String[] getHeader() {
        return header;
    }

    public void setHeader(String[] header) {
        this.header = header;
    }

    public TSVFileLoader() throws IOException {
    }

    public String[] loadLinesDataFromFile(String url) throws IOException {
        List<String> lines = new ArrayList<String>();

        FileReader fileReader = new FileReader(url);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        while ((line = bufferedReader.readLine()) != null){
            lines.add(line);
        }

        return lines.toArray(new String[0]);
    }

    public String[] loadHeader(String[] lines) throws NullPointerException{
        return lines[0].split("\t");
    }

    public boolean isBlankLine(String line){
        return line.trim().length() == 0;
    }

    public String[] getDataOfLine(String line){
        return line.split("\t");
    }

    public boolean isInvalidFileFormat(int dataLength, int headerLength){
        return dataLength != headerLength;
    }


    @Override
    public List<Contact> loadData(String url) throws IllegalAccessException {

        String[] lines = new String[0];
        try {
            lines = loadLinesDataFromFile(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        header = loadHeader(lines);

        TSVFileMapOrders tsvFileMapOrders = new TSVFileMapOrders(header);
        Field[] field = Contact.class.getFields();
        List<Contact> allContacts = new ArrayList<>();

        int blankCount = 0;
        int invalidLineCount = 0;
        for (int i=1; i < lines.length; i++) {

            if (isBlankLine(lines[i])) {
                blankCount++;
                continue;
            }

            String[] data = getDataOfLine(lines[i]);

            if (isInvalidFileFormat(data.length, header.length)) {
                invalidLineCount++;
                continue;
            }

            // TODO: I will use refection & annotations to build contact object later
            Contact contact = tsvFileMapOrders.getMapedData(data);

            allContacts.add(contact);
        }
        return allContacts;
    }
}
