package ma.norsys.educogest.controllers;

import ma.norsys.educogest.dto.DayScheduleDTO;
import ma.norsys.educogest.services.DayScheduleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_PAGE;
import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_SIZE;

@RestController
@RequestMapping("/api/dayschedule")
public class DayScheduleController {
    private final DayScheduleService dayScheduleService;

    public DayScheduleController(DayScheduleService dayScheduleService) {
        this.dayScheduleService = dayScheduleService;
    }

    @PostMapping("/add")
    public ResponseEntity<DayScheduleDTO> add(@RequestBody  DayScheduleDTO dayScheduleDTO) {
        DayScheduleDTO added = dayScheduleService.save(dayScheduleDTO);
        return new ResponseEntity<>(added, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        dayScheduleService.delete(id);

    }

    @GetMapping
    public ResponseEntity<List<DayScheduleDTO>> getAll(@RequestParam(defaultValue = DEFAULT_SIZE, required = false)
                              Integer pageSize,
                              @RequestParam(defaultValue = DEFAULT_PAGE, required = false)
                              Integer page) {
        Pageable paging = PageRequest.of(page, pageSize);
        List<DayScheduleDTO> dayScheduleDTOs = dayScheduleService.getAll(paging);
        return new ResponseEntity<>(dayScheduleDTOs, HttpStatus.OK);
    }
@GetMapping("/{id}")
    public ResponseEntity<DayScheduleDTO> get(@PathVariable Long id){
       DayScheduleDTO got= dayScheduleService.get(id);
    return new ResponseEntity<>(got, HttpStatus.CREATED);
}


}
