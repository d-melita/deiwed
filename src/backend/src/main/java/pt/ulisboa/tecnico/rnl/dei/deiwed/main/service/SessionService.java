package pt.ulisboa.tecnico.rnl.dei.deiwed.main.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.deiwed.exceptions.DeiwedException;
import pt.ulisboa.tecnico.rnl.dei.deiwed.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.deiwed.main.dto.SessionDto;
import pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity.Session;
import pt.ulisboa.tecnico.rnl.dei.deiwed.main.repository.SessionRepository;

@Service
@Transactional
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    private Session fetchSessionOrThrow(long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new DeiwedException(ErrorMessage.NO_SUCH_SESSION, Long.toString(id)));
    }

    public List<SessionDto> getAllSessions() {
        return sessionRepository.findAll().stream()
                .map(SessionDto::new)
                .collect(Collectors.toList());
    }

    public SessionDto createSession(SessionDto sessionDto) {
        Session session = new Session(sessionDto);
        session.setId(null); // to ensure that the id is generated by the database
        return new SessionDto(sessionRepository.save(session));
    }

    public SessionDto getSession(long id) {
        return new SessionDto(fetchSessionOrThrow(id));
    }

    public SessionDto updateSession(long id, SessionDto sessionDto) {
        fetchSessionOrThrow(id); // ensure exists
        Session session = new Session(sessionDto);
        session.setId(id);
        return new SessionDto(sessionRepository.save(session));
    }

    public void deleteSession(long id) {
        fetchSessionOrThrow(id); // ensure exists

        sessionRepository.deleteById(id);
    }
}