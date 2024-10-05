package ma.norsys.educogest.services;

import jakarta.persistence.EntityNotFoundException;
import ma.norsys.educogest.dto.StaffDTO;
import ma.norsys.educogest.models.Staff;
import org.springframework.data.domain.Pageable;
import ma.norsys.educogest.repository.StaffRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StaffService{

    private final StaffRepository staffRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }


    public StaffDTO saveStaff(StaffDTO staffDTO) {
        Staff staff = convertToEntity(staffDTO);
        Staff savedStaff = staffRepository.save(staff);
        return convertToDTO(savedStaff);
    }
    public List<StaffDTO> getAllStaff(Pageable pageable) {
        return staffRepository.findAll(pageable)
                .stream()
                .map(StaffService::convertToDTO)
                .collect(Collectors.toList());
    }

    public StaffDTO updateStaff(Long id, StaffDTO updatedStaffDTO) {
        Staff updatedStaff = staffRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Staff not found with id: " + id));

        if(updatedStaffDTO.getName() != null) updatedStaff.setName(updatedStaffDTO.getName());
        if(updatedStaffDTO.getFirstName() != null) updatedStaff.setFirstName(updatedStaffDTO.getFirstName());
        if(updatedStaffDTO.getPosition() != null) updatedStaff.setPosition(updatedStaffDTO.getPosition());

        Staff savedStaff = staffRepository.save(updatedStaff);
        return convertToDTO(savedStaff);

    }

    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }


    private static StaffDTO convertToDTO(Staff staff) {
        return modelMapper.map(staff,StaffDTO.class);
    }

    private Staff convertToEntity(StaffDTO staffDTO) {
        return modelMapper.map(staffDTO,Staff.class);
    }




}
