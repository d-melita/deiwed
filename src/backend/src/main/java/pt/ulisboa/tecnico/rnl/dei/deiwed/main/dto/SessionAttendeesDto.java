package pt.ulisboa.tecnico.rnl.dei.deiwed.main.dto;

import java.io.Serializable;

import pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity.SessionAttendees;

public class SessionAttendeesDto implements Serializable {
    private long id;
    private long sessionId;
    private long attendeeId;

    public SessionAttendeesDto() {
    }

    public SessionAttendeesDto(long id,long sessionId, long attendeeId) {
        this.id = id;
        this.sessionId = sessionId;
        this.attendeeId = attendeeId;
    }

    public SessionAttendeesDto(SessionAttendees sessionAttendeesDto) {
        this(sessionAttendeesDto.getId(), sessionAttendeesDto.getSessionId(), sessionAttendeesDto.getAttendeeId());
    }
    
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public long getAttendeeId() {
        return this.attendeeId;
    }

    public void setAttendeeId(long attendeeId) {
        this.attendeeId = attendeeId;
    }

    @Override
    public String toString() {
        return String.format(
            "SessionAttendeesDto[sessionId=%d, attendeeId=%d]", 
            sessionId, attendeeId);
    }
}
