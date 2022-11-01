package pt.ulisboa.tecnico.rnl.dei.deiwed.main.dto;

import java.io.Serializable;
import java.util.ArrayList;

import pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity.Session;
import pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity.Attendee;
import pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity.Dish;

// Data Transfer Object, to communicate with frontend
public class SessionDto implements Serializable {
    private long id;
    private String date;
    private String theme;
    private String speaker;
    private String image; // (it will be the file name)
    private ArrayList<Dish> dishes = new ArrayList<Dish>();
    private ArrayList<Attendee> attendees = new ArrayList<Attendee>();
    
    public SessionDto() {
    }

    public SessionDto(long id, String date, String theme, String speaker, String image) {
        this.id = id;
        this.date = date;
        this.theme = theme;
        this.speaker = speaker;
        this.image = image;
    }

    public SessionDto(Session session) {
        this(session.getId(), session.getDate(), session.getTheme(), session.getSpeaker(), session.getImage());
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTheme() {
        return this.theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getSpeaker() {
        return this.speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Dish> getDishes() {
        return this.dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public ArrayList<Attendee> getAttendees() {
        return this.attendees;
    }

    public void setAttendees(ArrayList<Attendee> attendees) {
        this.attendees = attendees;
    }

    @Override
    public String toString() {
        return String.format(
            "SessionDto [id=%s, date=%s, theme=%s, speaker=%s, image=%s, dishes=%s, attendees=%s]",
             id, date, theme, speaker, image, dishes, attendees);
    }
}