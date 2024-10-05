package ma.norsys.educogest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.norsys.educogest.models.Schedule;
import ma.norsys.educogest.models.Student;

import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupDTO {
        private Long id;
        private String groupCode;
        private String groupName;
        private String groupEmail;
        private String groupDescription;
        private boolean isSpecialGroup;
        private boolean isActive;
        private int maxCapacity;
        private Set<Student> students;
        private Schedule schedule;

    }

