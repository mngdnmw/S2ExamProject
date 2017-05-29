/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 *
 * @author Kristof
 */
public class Event {
    private int id;
    private Timestamp timestamp;
    private String description;
    private String time;

    public Event(int id, Timestamp date, String description) {
        this.id = id;
        this.timestamp = date;
        this.description = description;
        String temp = date.toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", " ");
        char[] tempchars = temp.toCharArray();
        temp = "";
        for (int i = 0; i < tempchars.length-4; i++) {
            temp += tempchars[i];
        }
        this.time = temp;
    }

    public Event(Timestamp date, String description) {
        this.timestamp = date;
        this.description = description;
        String temp = date.toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T", " ");
        char[] tempchars = temp.toCharArray();
        temp = "";
        for (int i = 0; i < tempchars.length-4; i++) {
            temp += tempchars[i];
        }
        this.time = temp;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
