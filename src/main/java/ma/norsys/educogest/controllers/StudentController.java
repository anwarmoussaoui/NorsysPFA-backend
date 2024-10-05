package ma.norsys.educogest.controllers;

import ma.norsys.educogest.dto.AbsenceDTO;
import ma.norsys.educogest.dto.StudentDTO;
import ma.norsys.educogest.services.AbsenceService;
import ma.norsys.educogest.services.StudentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_PAGE;
import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_SIZE;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    private final AbsenceService absenceService;

    public StudentController(StudentService studentService, AbsenceService absenceService) {
        this.studentService = studentService;
        this.absenceService = absenceService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents(@RequestParam(defaultValue = DEFAULT_SIZE, required = false)
                                                           Integer pageSize,
                                                           @RequestParam(defaultValue = DEFAULT_PAGE, required = false)
                                                           Integer page) {
        Pageable paging = PageRequest.of(page, pageSize);
        List<StudentDTO> studentDTOs = studentService.getAllStudents(paging);
        return new ResponseEntity<>(studentDTOs, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<StudentDTO>> searchStudents(@RequestParam String keyword,
                                                           @RequestParam(defaultValue = DEFAULT_SIZE, required = false) Integer pageSize,
                                                           @RequestParam(defaultValue = DEFAULT_PAGE, required = false) Integer page) {
        Pageable paging = PageRequest.of(page, pageSize);
        List<StudentDTO> studentDTOs = studentService.searchStudents(keyword, paging);
        return new ResponseEntity<>(studentDTOs, HttpStatus.OK);
    }

    @GetMapping("/{studentId}/isActive")
    public ResponseEntity<Boolean> checkStudentIsActive(@PathVariable Long studentId) {
        boolean isActive = studentService.checkStudentIsActive(studentId);
        return ResponseEntity.ok(isActive);
    }

    @GetMapping("/download-student-list")
    public ResponseEntity<String> downloadStudentListPDF() {
        String filePath = "C:\\Users\\HP\\Desktop\\ProjetDeStage\\List.pdf"; // change path
        try {
            studentService.generateStudentListPDF(filePath);
            return ResponseEntity.ok("List generated and saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error generating and saving list: " + e.getMessage());
        }
    }

    @GetMapping("/All-absences")
    public ResponseEntity<List<StudentDTO>> getAllStudentsWithAbsences() {
        List<StudentDTO> studentDTOs = studentService.getAllStudentsWithAbsences();
        return new ResponseEntity<>(studentDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> saveStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO savedStudentDTO = studentService.saveStudent(studentDTO);
        return new ResponseEntity<>(savedStudentDTO, HttpStatus.CREATED);
    }
    @GetMapping("/{identifier}")
    public ResponseEntity<List<AbsenceDTO>> getAbsencesByStudentIdentifier(@PathVariable String identifier) {
        List<AbsenceDTO> absences = absenceService.getAbsencesByStudentIdentifier(identifier);
        return ResponseEntity.ok(absences);}
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable Long id,
            @RequestBody StudentDTO updatedStudentDTO
    ) {
        StudentDTO studentDTO = studentService.updateStudent(id, updatedStudentDTO);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/deleteAbsence/{studentID}/{absenceID}")
        public void deleteAbsence(@PathVariable Long studentID,@PathVariable Long absenceID){
        studentService.removeAbsenceFromStudent(studentID,absenceID);
        }
}