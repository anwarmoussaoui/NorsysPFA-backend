package ma.norsys.educogest.controllers;


import ma.norsys.educogest.constants.Mappings;
import ma.norsys.educogest.dto.StaffDTO;
import ma.norsys.educogest.services.StaffService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_PAGE;
import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_SIZE;

@RestController
@RequestMapping(value = Mappings.REQUEST_STAFF)
public class StaffController {
    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping(value = Mappings.SHOW_STAFF)
    public ResponseEntity<List<StaffDTO>> getAllStaff(@RequestParam(defaultValue = DEFAULT_SIZE, required = false)
                                                      Integer pageSize,
                                                      @RequestParam(defaultValue = DEFAULT_PAGE, required = false)
                                                      Integer page){
        Pageable paging = PageRequest.of(page, pageSize);
        List<StaffDTO> staffDTOS = staffService.getAllStaff(paging);
        return new ResponseEntity<>(staffDTOS, HttpStatus.OK);
    }
    @PostMapping(value = Mappings.CREATE_STAFF)
    public ResponseEntity<StaffDTO> saveStaff(@RequestBody StaffDTO staffDTO) {
        StaffDTO savedStaffDTO = staffService.saveStaff(staffDTO);
        return new ResponseEntity<>(savedStaffDTO, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<StaffDTO> updateStaff(
            @PathVariable Long id,
            @RequestBody StaffDTO updatedStaffDTO
    ) {
        StaffDTO staffDTO = staffService.updateStaff(id, updatedStaffDTO);
        return new ResponseEntity<>(staffDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
