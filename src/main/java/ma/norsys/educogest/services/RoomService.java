package ma.norsys.educogest.services;


import jakarta.persistence.EntityNotFoundException;
import ma.norsys.educogest.dto.RoomDTO;
import ma.norsys.educogest.models.Room;
import ma.norsys.educogest.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private static final ModelMapper modelMapper = new ModelMapper();


    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RoomDTO saveRoom(RoomDTO roomDTO){
        Room room = convertToEntity(roomDTO);
        Room savedRoom = roomRepository.save(room);
        return convertToDTO(savedRoom);
    }

    public List<RoomDTO> getAllRooms(Pageable pageable){
        return roomRepository.findAll(pageable)
                .stream()
                .map(RoomService::convertToDTO)
                .collect(Collectors.toList());
    }


    public RoomDTO updateRoom(Long id,RoomDTO updatedRoomDTO){
        Room updatedRoom = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + id));
        if(updatedRoomDTO.getRoomInfo()!=null) updatedRoom.setRoomInfo(updatedRoomDTO.getRoomInfo());

        Room savedRoom = roomRepository.save(updatedRoom);
        return convertToDTO(savedRoom);
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }

    private static RoomDTO convertToDTO(Room room) {
        return modelMapper.map(room, RoomDTO.class);
    }

    private Room convertToEntity(RoomDTO  roomDTO) {
        return modelMapper.map(roomDTO, Room.class);
    }
}
