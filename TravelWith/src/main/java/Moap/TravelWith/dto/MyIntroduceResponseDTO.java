package Moap.TravelWith.dto;


// 자기소개글 및 프로필 정보 일부
public class MyIntroduceResponseDTO {
    private Long id;
    private String name;
    private String profileImg;
    private String gender;
    private String title;
    private String contents;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getProfileImg() { return profileImg; }
    public String getGender() { return gender; }
    public String getTitle() { return title; }
    public String getContents() { return contents; }


    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setProfileImg(String profileImg) { this.profileImg = profileImg; }
    public void setGender(String gender) { this.gender = gender; }
    public void setTitle(String title) { this.title = title; }
    public void setContents(String contents) { this.contents = contents; }
}
