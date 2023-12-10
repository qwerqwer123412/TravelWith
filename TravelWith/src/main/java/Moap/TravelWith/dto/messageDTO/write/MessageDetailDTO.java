package Moap.TravelWith.dto.messageDTO.write;

import Moap.TravelWith.entity.Message;
import Moap.TravelWith.enumer.Me;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDetailDTO {
    private String senderEmail;
    private String receiverEmail;
    private String title;
    private String contents;
    private LocalDateTime sendTime;
    private Me me;

    @Builder
    public MessageDetailDTO(String senderEmail, String receiverEmail, String title, String contents, LocalDateTime sendTime) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.title = title;
        this.contents = contents;
        this.sendTime = sendTime;
    }






    public static MessageDetailDTO entityToDTO(Message message){
        return MessageDetailDTO.builder()
                .receiverEmail(message.getReceiver().getEmail())
                .senderEmail(message.getSender().getEmail())
                .contents(message.getContents())
                .title(message.getTitle())
                .sendTime(message.getSendTime())
                .build();
    }

}