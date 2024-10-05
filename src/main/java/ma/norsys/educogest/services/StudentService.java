package ma.norsys.educogest.services;

import jakarta.persistence.EntityNotFoundException;
import ma.norsys.educogest.dto.AbsenceDTO;
import ma.norsys.educogest.dto.StudentDTO;
import ma.norsys.educogest.models.Absence;
import ma.norsys.educogest.models.Student;
import ma.norsys.educogest.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import java.io.IOException;
import java.util.Optional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final AbsenceService absenceService;
    private static final ModelMapper modelMapper = new ModelMapper();


    public StudentService(StudentRepository studentRepository, AbsenceService absenceService) {
        this.studentRepository = studentRepository;
        this.absenceService = absenceService;
    }
    public List<StudentDTO> getAllStudentsWithAbsences() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .filter(student -> hasAbsences(student))
                .map(this::convertToStudentDTOWithAbsences)
                .collect(Collectors.toList());
    }

    private boolean hasAbsences(Student student) {
        List<AbsenceDTO> absenceDTOs = absenceService.getAbsencesByStudentIdentifier(student.getIdentifier());
        return !absenceDTOs.isEmpty();
    }

    private StudentDTO convertToStudentDTOWithAbsences(Student student) {
        List<AbsenceDTO> absenceDTOs = absenceService.getAbsencesByStudentIdentifier(student.getIdentifier());
        List<Absence> absences = absenceService.convertToAbsenceList(absenceDTOs);

        return StudentDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .identifier(student.getIdentifier())
                .absences(absences)
                .build();
    }
    private static AbsenceDTO convertToAbsence(Absence absence) {
        return modelMapper.map(absence, AbsenceDTO.class);
    }
    public List<StudentDTO> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .stream()
                .map(StudentService::convertToDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO saveStudent(StudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    public StudentDTO updateStudent(Long id, StudentDTO updatedStudentDTO) {
        Student updatedStudent = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        if(updatedStudentDTO.getAddress() != null) updatedStudent.setAddress(updatedStudentDTO.getAddress());
        if(updatedStudentDTO.getName() != null)    updatedStudent.setName(updatedStudentDTO.getName());
        if(updatedStudentDTO.getFirstName() != null) updatedStudent.setFirstName(updatedStudentDTO.getFirstName());
        if(updatedStudentDTO.getIdentifier() != null) updatedStudent.setIdentifier(updatedStudentDTO.getIdentifier());


        Student savedStudent = studentRepository.save(updatedStudent);
        return convertToDTO(savedStudent);
    }
    public Student getStudentById(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        return optionalStudent.orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));
    }


    public List<StudentDTO> searchStudents(String keyword, Pageable pageable) {
        return studentRepository.findByIdentifierContainingIgnoreCaseOrNameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrAddressContainingIgnoreCase(
                        keyword, keyword, keyword, keyword, pageable)
                .stream()
                .map(StudentService::convertToDTO)
                .collect(Collectors.toList());
    }


    public boolean checkStudentIsActive(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));
        return student.getIsActive();
    }


    public void generateStudentListPDF(String filePath) {
        List<Student> students = studentRepository.findAll();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                float margin = 50;
                float yPosition = page.getMediaBox().getHeight() - margin;

                float[] columnWidths = {100, 100, 100, 100};

                writeTableRow(
                        contentStream,
                        margin,
                        yPosition,
                        PDType1Font.HELVETICA_BOLD,
                        columnWidths,
                        "Name", "First Name", "Date of Birth", "Identifier"
                );

                yPosition -= 20;

                for (Student student : students) {
                    writeTableRow(contentStream, margin, yPosition, PDType1Font.HELVETICA, columnWidths,
                            student.getName(), student.getFirstName(), student.getDateOfBirth().toString(), student.getIdentifier());
                    yPosition -= 20;
                }
            }

            document.save(filePath);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while generating the PDF.");
        }

    }

    private void writeTableRow(PDPageContentStream contentStream, float x, float y, PDType1Font font, float[] columnWidths, String... rowData) throws IOException {
        float currentX = x;
        float cellHeight = 20;

        PDColor blackColor = new PDColor(new float[]{0, 0, 0}, PDDeviceRGB.INSTANCE);

        contentStream.setStrokingColor(blackColor);

        contentStream.setLineWidth(1);

        for (int i = 0; i < rowData.length; i++) {
            contentStream.setFont(font, 10);

            contentStream.moveTo(currentX, y);
            contentStream.lineTo(currentX + columnWidths[i], y);
            contentStream.stroke();

            contentStream.moveTo(currentX, y);
            contentStream.lineTo(currentX, y - cellHeight);
            contentStream.stroke();

            contentStream.moveTo(currentX + columnWidths[i], y);
            contentStream.lineTo(currentX + columnWidths[i], y - cellHeight);
            contentStream.stroke();

            contentStream.beginText();
            contentStream.newLineAtOffset(currentX + 2, y - 15); // Offset for text within cell
            contentStream.showText(rowData[i]);
            contentStream.endText();

            currentX += columnWidths[i];
        }

        contentStream.moveTo(x, y - cellHeight);
        contentStream.lineTo(currentX, y - cellHeight);
        contentStream.stroke();
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    private static StudentDTO convertToDTO(Student student) {
        return modelMapper.map(student, StudentDTO.class);
    }

    private Student convertToEntity(StudentDTO studentDTO) {
        return modelMapper.map(studentDTO, Student.class);
    }
    public void removeAbsenceFromStudent(Long studentId, Long absenceId) {
        // Retrieve the student from the database
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));

        // Find the absence to be removed
        Absence absenceToRemove = null;
        for (Absence absence : student.getAbsences()) {
            if (absence.getId().equals(absenceId)) {
                absenceToRemove = absence;
                break;
            }
        }

        // If the absence was found, remove it
        if (absenceToRemove != null) {
            student.getAbsences().remove(absenceToRemove);
            // Update the student entity
            studentRepository.save(student);
        } else {
            throw new EntityNotFoundException("Absence not found with id: " + absenceId);
        }
    }
    public StudentDTO addAbsenceToStudentByIdentifier(String identifier, AbsenceDTO absenceDTO) {
        Student student = studentRepository.findByIdentifier(identifier);
        if (student == null) {
            throw new EntityNotFoundException("Student not found with identifier: " + identifier);
        }
        Absence absence = convertToEntity(absenceDTO);
        student.addAbsence(absence);
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);}
    private Absence convertToEntity(AbsenceDTO absenceDTO) {
        Absence absence = new Absence();
        absence.setAbsenceDate(absenceDTO.getAbsenceDate());
        absence.setReason(absenceDTO.getReason());
        return absence;
    }
    private AbsenceDTO convertToDTO(Absence absence) {
        AbsenceDTO absenceDTO = new AbsenceDTO();
        absenceDTO.setAbsenceDate(absence.getAbsenceDate());
        absenceDTO.setReason(absence.getReason());
        return absenceDTO;}


}