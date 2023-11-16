package Moap.TravelWith.controller;


import Moap.TravelWith.dto.IntroduceListDTO;
import Moap.TravelWith.dto.MyIntroduceDTO;
import Moap.TravelWith.dto.MyIntroduceResponseDTO;
import Moap.TravelWith.entity.Member;
import Moap.TravelWith.entity.MyIntroduce;
import Moap.TravelWith.repository.MemberRepository;
import Moap.TravelWith.repository.MyIntroduceRepository;
import Moap.TravelWith.service.MyIntroduceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/introduce")
public class MyIntroduceController {

    private final MyIntroduceService myIntroduceService;
    private final MemberRepository memberRepository;
    private final MyIntroduceRepository myIntroduceRepository;

    @Autowired
    public MyIntroduceController(MyIntroduceService myIntroduceService, MemberRepository memberRepository, MyIntroduceRepository myIntroduceRepository) {
        this.myIntroduceService = myIntroduceService;
        this.memberRepository = memberRepository;
        this.myIntroduceRepository = myIntroduceRepository;

    }

    //자기소개 글 작성
    @PostMapping("/write")
    public ResponseEntity<MyIntroduce> createMyIntroduce(@RequestBody MyIntroduceDTO myIntroduceDTO,
                                                         HttpSession session) {
        // 세션에서 memberId 가져옴
        Long memberId = (Long) session.getAttribute("memberId");
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        MyIntroduce createdIntroduce = myIntroduceService.createMyIntroduce(myIntroduceDTO, memberId);
        return new ResponseEntity<>(createdIntroduce, HttpStatus.CREATED);
    }

    //해당 id 자기소개글 클릭시 이동
    @GetMapping("/{memberId}")
    public ResponseEntity<MyIntroduceResponseDTO> getMyIntroduce(@PathVariable Long memberId) {
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        if (!memberOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Member member = memberOpt.get();
        Optional<MyIntroduce> myIntroduceOpt = myIntroduceRepository.findByMember(member);
        if (!myIntroduceOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        MyIntroduce myIntroduce = myIntroduceOpt.get();
        MyIntroduceResponseDTO response = new MyIntroduceResponseDTO();

        response.setId(member.getId());
        response.setName(member.getName());
        response.setProfileImg(member.getProfileImg());
        response.setGender(member.getGender().toString());
        response.setTitle(myIntroduce.getTitle());
        response.setContents(myIntroduce.getContents());

        return ResponseEntity.ok(response);
    }

    // 자기소개글 리스트 (id까지 보여주는게 좋을듯) -> 클릭해서 바로 이입
    @GetMapping("/list")
    public ResponseEntity<Map<String, List<IntroduceListDTO>>> getIntroduceList() {
        List<MyIntroduce> introduces = myIntroduceRepository.findAll();
        List<IntroduceListDTO> introduceListDTOS = introduces.stream().map(introduce -> {
            IntroduceListDTO dto = new IntroduceListDTO();
            dto.setName(introduce.getMember().getName());
            dto.setGender(introduce.getMember().getGender().toString());
            dto.setTitle(introduce.getTitle());
            dto.setContents(introduce.getContents());
            return dto;
        }).collect(Collectors.toList());

        Map<String, List<IntroduceListDTO>> response = new HashMap<>();
        response.put("introduce_list", introduceListDTOS);
        return ResponseEntity.ok(response);
    }
}
