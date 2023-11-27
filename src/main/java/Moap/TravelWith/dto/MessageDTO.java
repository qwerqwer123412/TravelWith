package Moap.TravelWith.dto;

public class MessageDTO {

    private String receiverId;
    private String title;
    private String contents;


    public MessageDTO() {
    }

    public MessageDTO(String receiverId, String title, String contents) {
        this.receiverId = receiverId;
        this.title = title;
        this.contents = contents;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
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
}