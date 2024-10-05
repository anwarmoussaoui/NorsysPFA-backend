package ma.norsys.educogest.controllers;

import ma.norsys.educogest.dto.ScheduleDTO;

import ma.norsys.educogest.services.ScheduleService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;



import java.util.List;

import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_PAGE;
import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_SIZE;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
       scheduleService.deleteSchedule(id);

    }
    @PostMapping("/add")
    public ResponseEntity<ScheduleDTO> add(@RequestBody ScheduleDTO schedule){
       return new ResponseEntity<>(scheduleService.saveSchedule(schedule),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> getone(@PathVariable Long id){

        ScheduleDTO got=  scheduleService.getById(id);
        return new ResponseEntity<>(got, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> get(@RequestParam(defaultValue = DEFAULT_SIZE, required = false)
                              Integer pageSize,
                              @RequestParam(defaultValue = DEFAULT_PAGE, required = false)
                              Integer page) {
        Pageable paging = PageRequest.of(page, pageSize);
        List<ScheduleDTO> scheduleDTOs = scheduleService.getAllSchedule(paging);
        return new ResponseEntity<>(scheduleDTOs, HttpStatus.OK);
    }}


