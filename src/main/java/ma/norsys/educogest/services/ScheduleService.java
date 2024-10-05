package ma.norsys.educogest.services;

import ma.norsys.educogest.dto.ScheduleDTO;
import ma.norsys.educogest.models.Schedule;
import ma.norsys.educogest.repository.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    static final ModelMapper modelMapper = new ModelMapper();

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
    public ScheduleDTO saveSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = convertToEntity(scheduleDTO);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return convertToDTO(savedSchedule);
    }
    public List<ScheduleDTO> getAllSchedule(Pageable pageable) {
        return scheduleRepository.findAll(pageable).stream().map(ScheduleService::convertToDTO) .collect(Collectors.toList());
    }
    private static ScheduleDTO convertToDTO(Schedule schedule) {
        return modelMapper.map(schedule, ScheduleDTO.class);
    }
    public ScheduleDTO getById(Long id) {
        return convertToDTO(scheduleRepository.findById(id).orElse(new Schedule()));
    }

    private Schedule convertToEntity(ScheduleDTO scheduleDTO) {
        return modelMapper.map(scheduleDTO, Schedule.class);
    }


    /* public ScheduleDTO update(Long id, ScheduleDTO schedule) {
        return convertToDTO(scheduleRepository.findById(id)
                .map(p-> {
                    //p.setDaySchedules(schedule.getDaySchedules());
                    return scheduleRepository.save(p);
                }).orElseThrow(() -> new RuntimeException("schedule not found")));
    }*/
}
