package ma.norsys.educogest.controllers;

import ma.norsys.educogest.constants.Mappings;
import ma.norsys.educogest.dto.ParentDTO;
import ma.norsys.educogest.services.ParentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Mappings.SIGN_YUP_PAGE)
public class ParentController {
    @GetMapping("/parents")
    public ResponseEntity<List<ParentDTO>> getAllParents(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ParentDTO> parentDTOs = parentService.getAllParents(pageable);
        return new ResponseEntity<>(parentDTOs, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<ParentDTO> saveParent(@RequestBody ParentDTO parentDTO) {
        ParentDTO savedParentDTO = parentService.saveParent(parentDTO);
        return new  ResponseEntity<>(savedParentDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParentDTO> updateParent(@PathVariable long id, @RequestBody ParentDTO updateParentDTO) {
        ParentDTO parentDTO = parentService.updateParent(id,updateParentDTO);
        return new  ResponseEntity<>(parentDTO, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delateParent(@PathVariable long id) {

        parentService.delateParent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }
}
