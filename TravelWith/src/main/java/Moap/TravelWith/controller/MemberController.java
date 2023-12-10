package Moap.TravelWith.controller;

import Moap.TravelWith.dto.MyPageDTO;
import Moap.TravelWith.dto.signup.SignupDTO;
import Moap.TravelWith.entity.Member;
import Moap.TravelWith.exception.NoLoginMemberFoundException;
import Moap.TravelWith.repository.LoginCheckRepository;
import Moap.TravelWith.repository.MemberRepository;
import Moap.TravelWith.service.ImageService;
import Moap.TravelWith.service.MemberService;
import Moap.TravelWith.service.MyIntroduceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor

public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final LoginCheckRepository loginCheckRepository;
    private final ImageService imageService;
    private final MyIntroduceService myIntroduceService;


    // 회원가입
    @PostMapping("/signUp")
    public ResponseEntity<Member> registerMember(@RequestBody SignupDTO signupDTO) {
        Member member = SignupDTO.dtoToEntity(signupDTO);
        Member registeredMember = memberService.registerNewMember(member);
        return new ResponseEntity<>(registeredMember, HttpStatus.CREATED);
    }

    @PostMapping("change-img")
    public ResponseEntity<?> changeProfileImg(@RequestParam MultipartFile file,
                                              @RequestHeader String email) throws IOException {
        loginCheck(email);
        Member member = memberRepository.findByEmail(email).get();
        String returnURL = imageService.changeProfileImg(file, member);
        return ResponseEntity.status(HttpStatus.OK).body(returnURL);
    }
    @GetMapping("/current-user")
    public ResponseEntity<MyPageDTO> getCurrentUser(@RequestHeader String email) {
        loginCheck(email);
        MyPageDTO myPageDTO = myIntroduceService.myPage(email);
        return ResponseEntity.ok().body(myPageDTO);

    }

    private void loginCheck(String email) {
        if (loginCheckRepository.findLoginCheckByEmail(email).isEmpty()) {
            throw new NoLoginMemberFoundException("로그인 한 회원이 존재하지 않습니다");
        }
    }
}