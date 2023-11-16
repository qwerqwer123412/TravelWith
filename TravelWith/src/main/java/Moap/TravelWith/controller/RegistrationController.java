package Moap.TravelWith.controller;

import Moap.TravelWith.entity.Member;
import Moap.TravelWith.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class RegistrationController {

    private final MemberService memberService;

    @Autowired
    public RegistrationController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Member> registerMember(@RequestBody Member member) {
        Member registeredMember = memberService.registerNewMember(member);
        return new ResponseEntity<>(registeredMember, HttpStatus.CREATED);
    }
}