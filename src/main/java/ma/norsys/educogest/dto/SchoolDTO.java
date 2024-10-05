package ma.norsys.educogest.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SchoolDTO {

    private Long id;

    private String schoolInfo;

    private String remarks;
}