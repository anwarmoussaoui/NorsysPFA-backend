package ma.norsys.educogest.controllers;

import ma.norsys.educogest.dto.ScheduleDTO;
import ma.norsys.educogest.dto.SessionDTO;
import ma.norsys.educogest.dto.StudentDTO;
import ma.norsys.educogest.services.SessionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_PAGE;
import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_SIZE;

@Controller
@RequestMapping("api/session")
public class SessionController {
    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<SessionDTO> getone(@PathVariable Long id){
        SessionDTO sessionDTO = sessionService.getone(id);
        return new ResponseEntity<>(sessionDTO, HttpStatus.OK);

    }
    @GetMapping
    public ResponseEntity<List<SessionDTO>> getAll(@RequestParam(defaultValue = DEFAULT_SIZE, required = false)
                                                 Integer pageSize,
                                                 @RequestParam(defaultValue = DEFAULT_PAGE, required = false)
                                                 Integer page) {
        Pageable paging = PageRequest.of(page, pageSize);
        List<SessionDTO> sessionDTOS = sessionService.getAll(paging);
        return new ResponseEntity<>(sessionDTOS, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<SessionDTO> add(SessionDTO sessionDTO){
        return new ResponseEntity<>(sessionService.save(sessionDTO),HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        sessionService.delete(id);
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<SessionDTO> update(@PathVariable Long id,@RequestBody SessionDTO sessionDTO){
        return  new ResponseEntity<>(sessionService.update(id,sessionDTO),HttpStatus.OK);

    }
}
