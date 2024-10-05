package ma.norsys.educogest.controllers;


import ma.norsys.educogest.constants.Mappings;
import ma.norsys.educogest.dto.RoomDTO;

import ma.norsys.educogest.services.RoomService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_PAGE;
import static ma.norsys.educogest.constants.PageRequestSizePage.DEFAULT_SIZE;

@RestController
@RequestMapping(value = Mappings.REQUEST_ROOM)
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @GetMapping(value = Mappings.SHOW_ROOM)
    public ResponseEntity<List<RoomDTO>> getAllRooms(@RequestParam(defaultValue = DEFAULT_SIZE, required = false)
    Integer pageSize,
                                                     @RequestParam(defaultValue = DEFAULT_PAGE, required = false)
    Integer page){
        Pageable paging = PageRequest.of(page,pageSize);
        List<RoomDTO> roomDTOS=roomService.getAllRooms(paging);
        return new ResponseEntity<>(roomDTOS, HttpStatus.OK);
    }

    @PostMapping(value = Mappings.CREATE_ROOM)
    public ResponseEntity<RoomDTO> saveRoom(@RequestBody RoomDTO roomDTO){
        RoomDTO savedRoomDTO=roomService.saveRoom(roomDTO);
        return new ResponseEntity<>(savedRoomDTO,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id,@RequestBody RoomDTO updatedRoomDTO){
        RoomDTO roomDTO = roomService.updateRoom(id, updatedRoomDTO);
        return new ResponseEntity<>(roomDTO, HttpStatus.OK);
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
