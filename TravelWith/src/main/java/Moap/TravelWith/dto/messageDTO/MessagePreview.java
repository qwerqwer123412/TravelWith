package Moap.TravelWith.dto.messageDTO;

import Moap.TravelWith.dto.match_response.MemberInfoDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class MessagePreview {
    MemberInfoDTO memberInfoDTO;
    String contents;
    String title;
    LocalDateTime rencentDate;

}


