package Moap.TravelWith.controller;

import Moap.TravelWith.dto.MemberDTO;
import Moap.TravelWith.entity.LoginCheck;
import Moap.TravelWith.entity.Member;
import Moap.TravelWith.exception.NoLoginMemberFoundException;
import Moap.TravelWith.repository.LoginCheckRepository;
import Moap.TravelWith.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final MemberRepository memberRepository;
    private final LoginCheckRepository loginCheckRepository;


    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Member> member = memberRepository.findByEmail(loginRequest.getEmail());

        if (!member.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed: User not found.");
        }
        if (member.get().getPassword().equals(loginRequest.getPassword())) {
            LoginCheck loginCheck = new LoginCheck(loginRequest.email);
            loginCheckRepository.findLoginCheckByEmail(loginRequest.email).orElseGet(() -> loginCheckRepository.save(loginCheck));
            // 인증 성공, 세션에 사용자 ID 저장
            return ResponseEntity.ok().body(loginRequest.getEmail());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed: Incorrect password.");
        }
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader String email) {
        loginCheck(email);
        loginCheckRepository.findLoginCheckByEmail(email).orElseThrow(() -> new RuntimeException("no MemberFindException"));
        return ResponseEntity.ok().body("Logged out successfully.");
    }

    static class LoginRequest {
        private String email;
        private String password;

        // getters and setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    /*
    // 로그인 사용자 인식 - 테스트용
    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(HttpSession session, @RequestHeader String email) {
        loginCheck(email);
        Long memberId = (Long) session.getAttribute("memberId");

        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user is currently logged in.");
        } else {
            Optional<Member> memberOptional = memberRepository.findById(memberId);
            if (memberOptional.isPresent()) {
                Member member = memberOptional.get();
                MemberDTO memberProfile = new MemberDTO(member.getId(), member.getEmail(), member.getName());
                return ResponseEntity.ok(memberProfile);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        }
    }
    */


    private void loginCheck(String email){
        if (!loginCheckRepository.findLoginCheckByEmail(email).isPresent()){
            throw new NoLoginMemberFoundException("로그인 한 회원이 존재하지 않습니다");
        }
    }
}
