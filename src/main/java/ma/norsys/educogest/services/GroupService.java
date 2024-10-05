package ma.norsys.educogest.services;

import jakarta.persistence.EntityNotFoundException;
import ma.norsys.educogest.dto.GroupDTO;
import ma.norsys.educogest.models.Group;
import ma.norsys.educogest.repository.GroupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    private static final ModelMapper modelMapper = new ModelMapper();

    public List<GroupDTO> getAllGroups(Pageable pageable) {
        return groupRepository.findAll(pageable)
                .stream()
                .map(GroupService::convertToDTO)
                .collect(Collectors.toList());
    }

    public GroupDTO saveGroup(GroupDTO groupDTO){

        Group group = convertToEntity(groupDTO);
        Group savedGroup = groupRepository.save(group);
        return convertToDTO(savedGroup);

    }

    public GroupDTO updateGroup(Long id, GroupDTO updatedGroupDTO) {
        Group updatedGroup = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + id));

        if (updatedGroupDTO.getGroupName() != null) updatedGroup.setGroupName(updatedGroupDTO.getGroupName());
        if (updatedGroupDTO.getGroupCode() != null) updatedGroup.setGroupCode(updatedGroupDTO.getGroupCode());
        if (updatedGroupDTO.getGroupEmail() != null) updatedGroup.setGroupEmail(updatedGroupDTO.getGroupEmail());
        if (updatedGroupDTO.getGroupDescription() != null)
            updatedGroup.setGroupDescription(updatedGroupDTO.getGroupDescription());
        if (updatedGroupDTO.getSchedule() != null) updatedGroup.setSchedule(updatedGroupDTO.getSchedule());
        if (updatedGroupDTO.getStudents() != null) updatedGroup.setStudents(updatedGroupDTO.getStudents());

        Integer maxCapacityDTO = updatedGroupDTO.getMaxCapacity();
        updatedGroup.setMaxCapacity(maxCapacityDTO);

        Group savedGroup = groupRepository.save(updatedGroup);
        return convertToDTO(savedGroup);
    }

        public void deleteGroup(Long id) {
            groupRepository.deleteById(id);
    }
    private static GroupDTO convertToDTO(Group group) {

        return modelMapper.map(group, GroupDTO.class);
    }
    private Group convertToEntity(GroupDTO groupDTO) {
        return modelMapper.map(groupDTO, Group.class);
    }

}
