package ma.norsys.educogest.services;

import jakarta.persistence.EntityNotFoundException;
import ma.norsys.educogest.dto.AbsenceDTO;
import ma.norsys.educogest.models.Absence;
import ma.norsys.educogest.models.Student;
import ma.norsys.educogest.repository.AbsenceRepository;
import ma.norsys.educogest.repository.ScheduleRepository;
import ma.norsys.educogest.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbsenceService {
    private final AbsenceRepository absenceRepository;
    private final StudentRepository studentrepository;

    public AbsenceService(AbsenceRepository absenceRepository, StudentRepository studentrepository) {
        this.absenceRepository = absenceRepository;
        this.studentrepository = studentrepository;
    }
    private static final ModelMapper modelMapper = new ModelMapper();
    public List<AbsenceDTO> getAllAbsences(Pageable pageable) {
        return absenceRepository.findAll(pageable)
                .stream()
                .map(AbsenceService::convertToDTO)
                .collect(Collectors.toList());
    }

    private static  AbsenceDTO convertToDTO(Absence absence) {
        return modelMapper.map(absence, AbsenceDTO.class);
    }

    private Absence convertToEntity(AbsenceDTO absenceDTO) {
        return modelMapper.map(absenceDTO, Absence.class);
    }
    public AbsenceDTO saveAbsence(AbsenceDTO absenceDTO) {
        Absence absence = convertToEntity(absenceDTO);
        Absence saveAbsence =absenceRepository.save(absence);
        return convertToDTO(saveAbsence);
    }
    public List<AbsenceDTO> getAbsencesByStudentIdentifier(String identifier) {
        Student student = studentrepository.findByIdentifier(identifier);
        List<Absence> absences = student.getAbsences();
        return absences.stream().map(AbsenceService::convertToDTO).collect(Collectors.toList());}

    public AbsenceDTO updateAbsence(long id, AbsenceDTO absenceDTO) {
        Absence absence =absenceRepository.findById(id).orElse(null);

        absence.setAbsenceDate(absenceDTO.getAbsenceDate());
        absence.setReason(absenceDTO.getReason());
        absence.setJustified(absenceDTO.isJustified());
        absence.setNumberOfDays(absenceDTO.getNumberOfDays());
        absence.setNotified(absenceDTO.isNotified());
        absence.setNotes(absence.getNotes());
        Absence saveabsence=absenceRepository.save(absence);
        return convertToDTO(saveabsence);
    }
    public List<Absence> convertToAbsenceList(List<AbsenceDTO> absenceDTOs) {
        return absenceDTOs.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    public void delateAbsence(long id){
        absenceRepository.deleteById(id);

    }
}
