package Moap.TravelWith.controller;


import Moap.TravelWith.dto.IntroduceListDTO;
import Moap.TravelWith.dto.MyIntroduceDTO;
import Moap.TravelWith.dto.MyIntroduceResponseDTO;
import Moap.TravelWith.entity.Member;
import Moap.TravelWith.entity.MyIntroduce;
import Moap.TravelWith.exception.NoLoginMemberFoundException;
import Moap.TravelWith.repository.LoginCheckRepository;
import Moap.TravelWith.repository.MemberRepository;
import Moap.TravelWith.repository.MyIntroduceRepository;
import Moap.TravelWith.service.MyIntroduceService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class MyIntroduceController {

    private final MyIntroduceService myIntroduceService;
    private final MemberRepository memberRepository;
    private final MyIntroduceRepository myIntroduceRepository;
    private final LoginCheckRepository loginCheckRepository;


    //자기소개 글 작성
    @PostMapping("/write")
    public ResponseEntity<MyIntroduce> createMyIntroduce(
            @RequestHeader String email,
            @RequestBody MyIntroduceDTO myIntroduceDTO) {
        // 세션에서 memberId 가져옴
        loginCheck(email);
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            Member member = byEmail.get();
            Long memberId = member.getId();
            MyIntroduce createdIntroduce = myIntroduceService.createMyIntroduce(myIntroduceDTO, memberId);
            return new ResponseEntity<>(createdIntroduce, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    //해당 자기소개글 클릭시 자기소개글로 이동
    @GetMapping("/{memberId}")
    public ResponseEntity<MyIntroduceResponseDTO> getMyIntroduce(@PathVariable Long memberId,
                                                                 @RequestHeader String email) {

        loginCheck(email);
        Optional<Member> memberOpt = memberRepository.findById(memberId);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            Optional<MyIntroduce> myIntroduceOpt = myIntroduceRepository.findByMember(member);
            if (myIntroduceOpt.isPresent()) {
                MyIntroduce myIntroduce = myIntroduceOpt.get();
                MyIntroduceResponseDTO response = new MyIntroduceResponseDTO();

                response.setId(member.getId());
                response.setName(member.getName());
                response.setGender(member.getGender().toString());
                response.setTitle(myIntroduce.getTitle());
                response.setContents(myIntroduce.getContents());

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }





    // 자기소개글 리스트 (id까지 보여주는게 좋을듯) -> 클릭해서 바로 이입
    @GetMapping("/list")
    public ResponseEntity<Map<String, List<IntroduceListDTO>>> getIntroduceList(
            @RequestHeader String email
    ) {

        loginCheck(email);
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

    private void loginCheck(String email) {
        if (loginCheckRepository.findLoginCheckByEmail(email).isEmpty()) {
            throw new NoLoginMemberFoundException("로그인 한 회원이 존재하지 않습니다");
        }
    }


}
