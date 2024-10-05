package ma.norsys.educogest.services;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Id;
import ma.norsys.educogest.dto.ParentDTO;
import ma.norsys.educogest.dto.StudentDTO;
import ma.norsys.educogest.models.Parent;
import ma.norsys.educogest.repository.ParentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParentService {
    private final ParentRepository parentRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    public ParentService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    public List<ParentDTO> getAllParents(Pageable pageable) {
        return parentRepository.findAll(pageable)
                .stream()
                .map(ParentService::convertToDTO)
                .collect(Collectors.toList());
    }

    private static ParentDTO convertToDTO(Parent parent) {
        return modelMapper.map(parent, ParentDTO.class);
    }

    private Parent convertToEntity(ParentDTO parentDTO) {
        return modelMapper.map(parentDTO, Parent.class);
    }

    public ParentDTO saveParent(ParentDTO parentDTO) {
        Parent parent = convertToEntity(parentDTO); // Appel de la méthode ici
        Parent saveParent = parentRepository.save(parent);
        return convertToDTO(saveParent);
    }

    public ParentDTO updateParent(long id, ParentDTO parentDTO) {
        Parent parent = parentRepository.findById(id).orElse(null);
        if (parent == null) {
            throw new EntityNotFoundException("Parent not found with id: " + id);
        }
        parent.setName(parentDTO.getName());
        parent.setFirstName(parentDTO.getFirstName());
        parent.setAddress(parentDTO.getAddress());
        parent.setPhoneNumber(parentDTO.getPhoneNumber());
        parent.setOccupation(parentDTO.getOccupation());
        parent.setEmailAddress(parentDTO.getEmailAddress());
        Parent saveparent= parentRepository.save(parent);
        return convertToDTO(saveparent);
    }

    public void delateParent(long id){ parentRepository.deleteById(id);}
}
