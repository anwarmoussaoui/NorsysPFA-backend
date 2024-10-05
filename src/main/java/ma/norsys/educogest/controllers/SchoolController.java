package ma.norsys.educogest.controllers;


import lombok.AllArgsConstructor;
import ma.norsys.educogest.dto.SchoolDTO;
import ma.norsys.educogest.services.SchoolService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ma.norsys.educogest.constants.Mappings;


import java.util.List;

import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_PAGE;
import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_SIZE;

@RestController
@AllArgsConstructor
@RequestMapping(value = Mappings.SCHOOL_PAGE)
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping
    public ResponseEntity<List<SchoolDTO>> getSchoolInfo(@RequestParam(defaultValue = DEFAULT_SIZE, required = false)
                                                         Integer pageSize,
                                                         @RequestParam(defaultValue = DEFAULT_PAGE, required = false)
                                                         Integer page){
        Pageable paging = PageRequest.of(page, pageSize);
        List<SchoolDTO> schoolDTOs = schoolService.getSchoolInfo(paging);
        return new ResponseEntity<>(schoolDTOs, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<SchoolDTO> saveSchool(@RequestBody SchoolDTO schoolDTO){
        SchoolDTO savedSchoolDTO = schoolService.saveSchool(schoolDTO);
        return new ResponseEntity<>(savedSchoolDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolDTO> updateSchool(
            @PathVariable Long id,
            @RequestBody SchoolDTO updatedSchoolDTO
    ){
        SchoolDTO schoolDTO = schoolService.updateSchool(id, updatedSchoolDTO);
        return new ResponseEntity<>(schoolDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id){
        schoolService.deleteSchool(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{id}")
    public ResponseEntity<SchoolDTO> getSchoolById(@PathVariable Long id) {
        SchoolDTO schoolDTO = schoolService.findById(id);
        return ResponseEntity.ok(schoolDTO);
}

}
