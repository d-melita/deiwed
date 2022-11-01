package pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import pt.ulisboa.tecnico.rnl.dei.deiwed.main.dto.SessionDto;

@Entity
@Table(name = "sessions")
public class Session {
    
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "theme", nullable = false)
    private String theme;

    @Column(name = "speaker", nullable = false)
    private String speaker;

    @Column(name = "image", nullable = false)
    private String image;


    protected Session() {
    }

    public Session(String date, String theme, String speaker, String image) {
        this.date = date;
        this.theme = theme;
        this.speaker = speaker;
        this.image = image;
    }

    public Session(SessionDto sessionDto) {
        this(sessionDto.getDate(), sessionDto.getTheme(), sessionDto.getSpeaker(), sessionDto.getImage());
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return String.format(
            "Session[id=%d, date='%s', theme='%s', speaker='%s', image='%s']",
            id, date, theme, speaker, image);
    }
}