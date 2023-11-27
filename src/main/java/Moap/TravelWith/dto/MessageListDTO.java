package Moap.TravelWith.dto;

import java.time.LocalDateTime;

public class MessageListDTO {
    private Long id; // 쪽지 ID
    private String counterpartyId; // 상대방의 ID (수신자 또는 발신자)
    private String title; // 쪽지 제목
    private LocalDateTime sendTime; // 보낸 시간

    public MessageListDTO(Long id, String counterpartyId, String title, LocalDateTime sendTime) {
        this.id = id;
        this.counterpartyId = counterpartyId;
        this.title = title;
        this.sendTime = sendTime;
    }

    // getter 및 setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCounterpartyId() {
        return counterpartyId;
    }

    public void setCounterpartyId(String counterpartyId) {
        this.counterpartyId = counterpartyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
