package Moap.TravelWith.dto;

// 자기소개 글 리스트
public class IntroduceListDTO {
    private String name;
    private String gender;
    private String title;
    private String contents;

    public IntroduceListDTO() {}

    public IntroduceListDTO(String name, String gender, String title, String contents) {
        this.name = name;
        this.gender = gender;
        this.title = title;
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
