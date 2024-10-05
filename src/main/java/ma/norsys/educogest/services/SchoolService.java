package ma.norsys.educogest.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import ma.norsys.educogest.dto.SchoolDTO;
import ma.norsys.educogest.models.School;
import ma.norsys.educogest.repository.SchoolRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    public List<SchoolDTO> getSchoolInfo(Pageable pageable) {
        return schoolRepository.findAll(pageable)
                .stream()
                .map(SchoolService::convertToDTO)
                .collect(Collectors.toList());
    }

    public SchoolDTO saveSchool(SchoolDTO schoolDTO){
        School school = convertToEntity(schoolDTO);
        School saveSchool = schoolRepository.save(school);
        return convertToDTO(saveSchool);
    }

    public SchoolDTO updateSchool(Long id, SchoolDTO updatedSchoolDTO){
        School updatedSchool = schoolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("School not found with id: " + id));
        if(updatedSchoolDTO.getSchoolInfo() != null) updatedSchool.setSchoolInfo(updatedSchoolDTO.getSchoolInfo());
        if(updatedSchoolDTO.getRemarks() != null) updatedSchool.setRemarks(updatedSchoolDTO.getRemarks());

        School savedSchool = schoolRepository.save(updatedSchool);
        return convertToDTO(savedSchool);
    }
    public SchoolDTO findById(Long id) {
        Optional<School> schoolOptional = schoolRepository.findById(id);

        if (schoolOptional.isPresent()) {
            School school = schoolOptional.get();
            return convertToDTO(school);
        } else {
            throw new EntityNotFoundException("School with ID " + id + " not found");
}}

    public void deleteSchool(Long id){
        schoolRepository.deleteById(id);
    }

    private static SchoolDTO convertToDTO(School school) {
        return modelMapper.map(school, SchoolDTO.class);
    }

    private School convertToEntity(SchoolDTO schoolDTO) {
        return modelMapper.map(schoolDTO, School.class);
    }
}
