package pt.ulisboa.tecnico.rnl.dei.deiwed.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity.SessionAttendees;

@Repository
@Transactional
public interface SessionAttendeesRepository extends JpaRepository<SessionAttendees, Long> {
    // SELECT * FROM session_attendees WHERE session_id = sessionId
    List<SessionAttendees> findBySessionId(long sessionId);

    // SELECT * FROM session_attendees WHERE attendee_id = attendeeId
    List<SessionAttendees> findByAttendeeId(long attendeeId);
    
    // DELETE FROM session_attendees WHERE session_id = sessionId AND attendee_id = attendeeId
    void deleteBySessionIdAndAttendeeId(long sessionId, long attendeeId);
}