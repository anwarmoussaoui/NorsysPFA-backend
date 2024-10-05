package ma.norsys.educogest.services;

import ma.norsys.educogest.dto.SessionDTO;
import ma.norsys.educogest.models.Session;
import ma.norsys.educogest.repository.SessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
    public void delete(Long id){
        sessionRepository.deleteById(id);
    }
    public SessionDTO getone(Long id){
        return convertToDTO(sessionRepository.findById(id).orElse(new Session()));

    }
    public List<SessionDTO> getAll(Pageable pageable){
        return sessionRepository.findAll(pageable).stream().map(SessionService::convertToDTO) .collect(Collectors.toList());
    }

    public SessionDTO save(SessionDTO sessionDTO){
        return convertToDTO(sessionRepository.save(convertToEntity(sessionDTO)));
    }
    public SessionDTO update(Long id, SessionDTO sessionDTO) {
        return convertToDTO(sessionRepository.findById(id)
                .map(p-> {
                    p.setStartTime(sessionDTO.getStartTime());
                    p.setEndTime(sessionDTO.getEndTime());
                    p.setRoom(sessionDTO.getRoom());
                    p.setSubject(sessionDTO.getSubject());
                    return sessionRepository.save(p);
                }).orElseThrow(() -> new RuntimeException("session not found")));
    }

    private static SessionDTO convertToDTO(Session session) {
        return modelMapper.map(session, SessionDTO.class);
    }

    private Session convertToEntity(SessionDTO sessionDTO) {
        return modelMapper.map(sessionDTO, Session.class);
}}
