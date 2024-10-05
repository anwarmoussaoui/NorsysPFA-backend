package ma.norsys.educogest.services;

import ma.norsys.educogest.dto.DayScheduleDTO;
import ma.norsys.educogest.dto.ScheduleDTO;
import ma.norsys.educogest.dto.StudentDTO;
import ma.norsys.educogest.models.DaySchedule;
import ma.norsys.educogest.models.Schedule;
import ma.norsys.educogest.models.Student;
import ma.norsys.educogest.repository.DayScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class DayScheduleService {
    private final DayScheduleRepository dayScheduleRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    public DayScheduleService(DayScheduleRepository dayScheduleRepository) {
        this.dayScheduleRepository = dayScheduleRepository;
    }
    public List<DayScheduleDTO> getAll(Pageable pageable) {
        return dayScheduleRepository.findAll(pageable)
                .stream().map(DayScheduleService::convertToDTO)
                .collect(Collectors.toList());
    }
    public void delete(Long id){
        dayScheduleRepository.deleteById(id);
    }
    public DayScheduleDTO save(DayScheduleDTO dayScheduleDTO) {
        DaySchedule daySchedule = convertToEntity(dayScheduleDTO);
        DaySchedule saved = (DaySchedule) dayScheduleRepository.save(daySchedule);
        return convertToDTO(saved);
    }
    public DayScheduleDTO get(Long id){
        return convertToDTO(dayScheduleRepository.findById(id).orElse(new DaySchedule()));
    }
    private static DayScheduleDTO convertToDTO(DaySchedule daySchedule) {
        return modelMapper.map(daySchedule, DayScheduleDTO.class);
    }

    private DaySchedule convertToEntity(DayScheduleDTO dayScheduleDTO) {
        return modelMapper.map(dayScheduleDTO, DaySchedule.class);
}
    public DayScheduleDTO update(Long id, DayScheduleDTO dayschedule) {
        return convertToDTO(dayScheduleRepository.findById(id)
                .map(p-> {
                    p.setSessions(dayschedule.getSessions());
                    p.setDayOfWeek(dayschedule.getDayOfWeek());
                    return dayScheduleRepository.save(p);
                }).orElseThrow(() -> new RuntimeException("schedule not found")));
    }}
