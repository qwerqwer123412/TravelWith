package Moap.TravelWith.dto.messageDTO.write;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageWriteDTO {

    private String receiverEmail;
    private String title;
    private String contents;



    @Builder
    public MessageWriteDTO(String receiverEmail, String title, String contents) {
        this.receiverEmail = receiverEmail;
        this.title = title;
        this.contents = contents;
    }


}