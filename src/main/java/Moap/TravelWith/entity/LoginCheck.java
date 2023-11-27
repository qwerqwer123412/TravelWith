package Moap.TravelWith.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class LoginCheck {

    @Id
    @GeneratedValue
    private Long id;


    private String email;

    public LoginCheck(String email) {
        this.email = email;
    }
}
