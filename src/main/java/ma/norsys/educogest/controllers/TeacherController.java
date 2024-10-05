package ma.norsys.educogest.controllers;
import ma.norsys.educogest.constants.Mappings;
import ma.norsys.educogest.dto.TeacherDTO;
import ma.norsys.educogest.models.Teacher;
import ma.norsys.educogest.services.TeacherService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    public final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    @GetMapping(value= Mappings.SIGN_PAGE)
    public ResponseEntity<List<TeacherDTO>> getAllTeachers(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size){
        Pageable pageable=PageRequest.of(page,size);
        List<TeacherDTO> teacherDTOS= teacherService.getAllTeachers(pageable);
        return new ResponseEntity<>(teacherDTOS,HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<TeacherDTO> saveTeacher(@RequestBody TeacherDTO teacherDTO) {
        TeacherDTO saveTeacherDTO = teacherService.saveTeacher(teacherDTO);
        return new  ResponseEntity<>(saveTeacherDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable long id, @RequestBody TeacherDTO updateTeacherDTO) {
        TeacherDTO teacherDTO = teacherService.updateTeacher(id,updateTeacherDTO);
        return new  ResponseEntity<>(teacherDTO, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delateTeacher(@PathVariable long id) {

        teacherService.delateTeacher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/absence")
    public ResponseEntity<List<Teacher>> teacherWithabs(){
        return new ResponseEntity<>(teacherService.getAllTeacherWithAbsences(),HttpStatus.OK);
    }
    @PostMapping("/deleteAbsence/{studentID}/{absenceID}")
    public void deleteAbsence(@PathVariable Long studentID,@PathVariable Long absenceID){
        teacherService.removeAbsenceFromTeacher(studentID,absenceID);
    }
}
