package Moap.TravelWith.controller;

import Moap.TravelWith.entity.Member;
import Moap.TravelWith.repository.MemberRepository;
import Moap.TravelWith.service.MemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class TestController {
    private final MemberService memberService;
    @GetMapping
    @ResponseBody
    public String contributing() {
        Member member = new Member();
        memberService.test(member);
        return member.toString();

    }
}
