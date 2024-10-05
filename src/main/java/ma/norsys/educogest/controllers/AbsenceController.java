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

@RestController
@RequestMapping("/Api/absence")
public class AbsenceController {
    private final AbsenceService absenceService;
    private final StudentService studentService;

    public AbsenceController(AbsenceService absenceService, StudentService studentService) {
        this.absenceService = absenceService;
        this.studentService = studentService;
    }
@GetMapping("/absences")
    public ResponseEntity<List<AbsenceDTO>> getAllAbsences(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<AbsenceDTO> absenceDTOS =absenceService.getAllAbsences(pageable);
        return new ResponseEntity<>(absenceDTOS, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<AbsenceDTO> saveAbsence(@RequestBody AbsenceDTO absenceDTO) {
        AbsenceDTO savedAbsenceDTO =absenceService.saveAbsence(absenceDTO);
        return new  ResponseEntity<>(savedAbsenceDTO, HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<AbsenceDTO> updateAbsence(@PathVariable long id, @RequestBody AbsenceDTO updateAbsenceDTO) {
        AbsenceDTO absenceDTO =absenceService.updateAbsence(id,updateAbsenceDTO);
        return new  ResponseEntity<>(absenceDTO, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delateAbsence(@PathVariable long id) {
        absenceService.delateAbsence(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/add-absenceStudent/{identifier}")
    public ResponseEntity<AbsenceDTO> addAbsenceToStudentByIdentifier(
            @PathVariable String identifier,
            @RequestBody AbsenceDTO absenceDTO
    ) {
        AbsenceDTO savedAbsenceDTO = absenceService.saveAbsence(absenceDTO);
        StudentDTO updatedStudentDTO = studentService.addAbsenceToStudentByIdentifier(identifier, savedAbsenceDTO);
        return new ResponseEntity<>(savedAbsenceDTO, HttpStatus.CREATED);
    }
}
