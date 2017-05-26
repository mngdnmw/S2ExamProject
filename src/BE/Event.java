/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import java.sql.Timestamp;

/**
 *
 * @author Kristof
 */
public class Event {
    private int id;
    private Timestamp timestamp;
    private String description;

    public Event(int id, Timestamp date, String description) {
        this.id = id;
        this.timestamp = date;
        this.description = description;
    }

    public Event(Timestamp date, String description) {
        this.timestamp = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
