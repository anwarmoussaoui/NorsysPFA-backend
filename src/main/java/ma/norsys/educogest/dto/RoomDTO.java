package ma.norsys.educogest.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RoomDTO {
    private Long id;
    private String roomInfo;
}