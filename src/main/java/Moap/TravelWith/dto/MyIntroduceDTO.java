package Moap.TravelWith.dto;

// 자기소개 글 작성
public class MyIntroduceDTO {
    private String title;
    private String contents;

    public MyIntroduceDTO() {}

    public MyIntroduceDTO(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    // Getters
    public String getTitle() { return title; }

    public String getContents() { return contents; }

    // Setters
    public void setTitle(String title) { this.title = title; }

    public void setContents(String contents) { this.contents = contents; }

    @Override
    public String toString() {
        return "MyIntroduceDTO{" +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
