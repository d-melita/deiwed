package pt.ulisboa.tecnico.rnl.dei.deiwed.main.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.deiwed.exceptions.DeiwedException;
import pt.ulisboa.tecnico.rnl.dei.deiwed.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.deiwed.main.dto.SessionAttendeesDto;
import pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity.SessionAttendees;
import pt.ulisboa.tecnico.rnl.dei.deiwed.main.repository.SessionAttendeesRepository;

@Service
@Transactional
public class SessionsAttendeesService {
    @Autowired
    private SessionAttendeesRepository sessionAttendeesRepository;

    private SessionAttendees fetchSessionAttendeesOrThrow(long id) {
        return sessionAttendeesRepository.findById(id)
                .orElseThrow(() -> new DeiwedException(ErrorMessage.NO_SUCH_SESSION_ATTENDEES, Long.toString(id)));
    }

    public List<SessionAttendeesDto> getAllSessionAttendees(long sessionId) {
        // SELECT * FROM session_attendees WHERE session_id = sessionId
        return sessionAttendeesRepository.findBySessionId(sessionId).stream().
                map(SessionAttendeesDto::new)
                .collect(Collectors.toList());
    }

    public List<SessionAttendeesDto> getAllAttendeeSessions(long attendeeId) {
        // SELECT * FROM session_attendees WHERE attendee_id = attendeeId
        return sessionAttendeesRepository.findByAttendeeId(attendeeId).stream().
                map(SessionAttendeesDto::new)
                .collect(Collectors.toList());
    }

    public SessionAttendeesDto createSessionAttendees(SessionAttendeesDto sessionAttendeesDto) {
        SessionAttendees sessionAttendees = new SessionAttendees(sessionAttendeesDto);
        sessionAttendees.setId(null); // to ensure that the id is generated by the database
        return new SessionAttendeesDto(sessionAttendeesRepository.save(sessionAttendees));
    }

    public SessionAttendeesDto getSessionAttendees(long id) {
        return new SessionAttendeesDto(fetchSessionAttendeesOrThrow(id));
    }

    public SessionAttendeesDto updateSessionAttendees(long id, SessionAttendeesDto sessionAttendeesDto) {
        fetchSessionAttendeesOrThrow(id); // ensure exists
        SessionAttendees sessionAttendees = new SessionAttendees(sessionAttendeesDto);
        sessionAttendees.setId(id);
        return new SessionAttendeesDto(sessionAttendeesRepository.save(sessionAttendees));
    }

    public void deleteAttendeeFromSession(long sessionId, long attendeeId) {
        sessionAttendeesRepository.deleteBySessionIdAndAttendeeId(sessionId, attendeeId);
    }
}