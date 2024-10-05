package ma.norsys.educogest.controllers;


import ma.norsys.educogest.constants.Mappings;
import ma.norsys.educogest.dto.GroupDTO;
import ma.norsys.educogest.services.GroupService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_PAGE;
import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_SIZE;

@RestController
@RequestMapping(value = Mappings.REQUEST_GROUP)
public class GroupController {

    private final GroupService groupService;


    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping(value = Mappings.SHOW_GROUP)
    public ResponseEntity<List<GroupDTO>> getAllGroups(@RequestParam(defaultValue = DEFAULT_SIZE, required = false)
                                                           Integer pageSize,
                                                         @RequestParam(defaultValue = DEFAULT_PAGE, required = false)
                                                           Integer page) {
        Pageable paging = PageRequest.of(page, pageSize);
        List<GroupDTO> groupDTOS = groupService.getAllGroups(paging);
        return new ResponseEntity<>(groupDTOS, HttpStatus.OK);
    }

    @PostMapping(value = Mappings.CREATE_GROUP)
    public ResponseEntity<GroupDTO> saveGroup(@RequestBody GroupDTO groupDTO) {
        GroupDTO savedGroupDTO = groupService.saveGroup(groupDTO);
        return new ResponseEntity<>(savedGroupDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> updateGroup(
            @PathVariable Long id,
            @RequestBody GroupDTO updatedGroupDTO
    ) {
        GroupDTO groupDTO = groupService.updateGroup(id, updatedGroupDTO);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
