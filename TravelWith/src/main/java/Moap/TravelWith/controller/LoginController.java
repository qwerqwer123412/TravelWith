package Moap.TravelWith.controller;

import Moap.TravelWith.dto.MemberDTO;
import Moap.TravelWith.entity.Member;
import Moap.TravelWith.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final MemberRepository memberRepository;

    @Autowired
    public LoginController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Optional<Member> member = memberRepository.findByEmail(loginRequest.getEmail());

        if (!member.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed: User not found.");
        }

        if (member.get().getPassword().equals(loginRequest.getPassword())) {
            // 인증 성공, 세션에 사용자 ID 저장
            session.setAttribute("memberId", member.get().getId());
            return ResponseEntity.ok().body("Login Successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Failed: Incorrect password.");
        }
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
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

    // 로그인 사용자 인식 - 테스트용
    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
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
}
