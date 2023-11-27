package Moap.TravelWith.dto;

import java.time.LocalDateTime;

public class MessageDetailDTO {
    private String counterparty; // 상대방 (수신자 또는 발신자)
    private String title; // 제목
    private String contents; // 내용
    private LocalDateTime sendTime; // 보낸 시간

    public MessageDetailDTO(String counterparty, String title, String contents, LocalDateTime sendTime) {
        this.counterparty = counterparty;
        this.title = title;
        this.contents = contents;
        this.sendTime = sendTime;
    }

    // getter 및 setter
    public String getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
