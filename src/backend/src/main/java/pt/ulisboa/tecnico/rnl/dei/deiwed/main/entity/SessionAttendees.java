package pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import pt.ulisboa.tecnico.rnl.dei.deiwed.main.dto.SessionAttendeesDto;

@Entity
@Table(name = "session_attendees")
public class SessionAttendees {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)

    private Long id;

    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    @Column(name = "attendee_id", nullable = false)
    private Long attendeeId;

    protected SessionAttendees() {
    }

    public SessionAttendees(Long id, Long sessionId, Long attendeeId) {
        this.id = id;
        this.sessionId = sessionId;
        this.attendeeId = attendeeId;
    }

    public SessionAttendees(SessionAttendeesDto sessionAttendeesDto) {
        this(sessionAttendeesDto.getId(), sessionAttendeesDto.getSessionId(), sessionAttendeesDto.getAttendeeId());
    }
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getAttendeeId() {
        return this.attendeeId;
    }

    public void setAttendeeId(Long attendeeId) {
        this.attendeeId = attendeeId;
    }

    @Override
    public String toString() {
        return String.format("SessionAttendees[sessionId=%d, attendeeId=%d]", sessionId, attendeeId);
    }
}