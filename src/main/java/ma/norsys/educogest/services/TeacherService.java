package ma.norsys.educogest.services;
import jakarta.persistence.EntityNotFoundException;
import ma.norsys.educogest.dto.AbsenceDTO;
import ma.norsys.educogest.dto.StudentDTO;
import ma.norsys.educogest.dto.TeacherDTO;
import ma.norsys.educogest.models.Absence;
import ma.norsys.educogest.models.Student;
import ma.norsys.educogest.models.Teacher;
import ma.norsys.educogest.repository.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    private static final ModelMapper modelmapper=new ModelMapper();
    public List<TeacherDTO> getAllTeachers(Pageable pageable){
        Page<Teacher> all = teacherRepository.findAll(pageable);
        all.forEach(f -> {
            List<Absence> absences = f.getAbsences();
            absences.forEach(System.out::println);
        });
        return all
                .stream()
                .map(TeacherService::convertToDTO)
                .collect(Collectors.toList());
    }
    private static TeacherDTO convertToDTO(Teacher teacher) {
        return modelmapper.map(teacher, TeacherDTO.class);
    }


    private Teacher convertToEntity(TeacherDTO teacherDTO) {
        return modelmapper.map(teacherDTO, Teacher.class);
    }
    public TeacherDTO saveTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = convertToEntity(teacherDTO); // Appel de la méthode ici
        Teacher saveTeacher =teacherRepository.save(teacher);
        return convertToDTO(saveTeacher);
    }

    public TeacherDTO updateTeacher(long id, TeacherDTO teacherDTO) {
        Teacher teacher =teacherRepository.findById(id).orElse(null);
        if (teacher == null) {
            throw new EntityNotFoundException("Teacher not found with id: " + id);
        }
        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setName(teacherDTO.getName());
        teacher.setPhoto(teacherDTO.getPhoto());
        teacher.setSubjectTaught(teacherDTO.getSubjectTaught());
        teacher.setSchedule(teacherDTO.getSchedule());
        Teacher saveTeacher=teacherRepository.save(teacher);
        return convertToDTO(saveTeacher);
    }

    public void delateTeacher(long id){teacherRepository.deleteById(id);}
    public List<Teacher> getAllTeacherWithAbsences() {
        List<Teacher> teacher = teacherRepository.findAll();
        return teacher.stream().filter(teacher1 -> hasAbsences(teacher1)).toList();
    }

    private boolean hasAbsences(Teacher teacher) {
        List<Absence> absenceDTOs = teacher.getAbsences();
        return !absenceDTOs.isEmpty();
    }
    public void removeAbsenceFromTeacher(Long teacherId, Long absenceId) {
        // Retrieve the student from the database
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + teacherId));

        // Find the absence to be removed
        Absence absenceToRemove = null;
        for (Absence absence : teacher.getAbsences()) {
            if (absence.getId().equals(absenceId)) {
                absenceToRemove = absence;
                break;
            }
        }

        // If the absence was found, remove it
        if (absenceToRemove != null) {
            teacher.getAbsences().remove(absenceToRemove);
            // Update the student entity
            teacherRepository.save(teacher);
        } else {
            throw new EntityNotFoundException("Absence not found with id: " + absenceId);
        }
    }

}
