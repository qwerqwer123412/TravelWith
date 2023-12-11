package Moap.TravelWith.service;

import Moap.TravelWith.dto.MyIntroduceDTO;
import Moap.TravelWith.dto.MyPageDTO;
import Moap.TravelWith.dto.match_response.MemberInfoDTO;
import Moap.TravelWith.entity.Member;
import Moap.TravelWith.entity.MyIntroduce;
import Moap.TravelWith.repository.MemberRepository;
import Moap.TravelWith.repository.MyIntroduceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyIntroduceService {

    private final MyIntroduceRepository myIntroduceRepository;
    private final MemberRepository memberRepository;



    public MyIntroduce createMyIntroduce(MyIntroduceDTO introduceDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        MyIntroduce myIntroduce = new MyIntroduce();
        myIntroduce.setMember(member);
        myIntroduce.setTitle(introduceDTO.getTitle());
        myIntroduce.setContents(introduceDTO.getContents());

        return myIntroduceRepository.save(myIntroduce);
    }

    public MyPageDTO myPage(String email){

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() ->new IllegalArgumentException("Invalid member ID"));
        Optional<MyIntroduce> myIntroduceOptional = myIntroduceRepository.findByMember(member);
        MyIntroduce myIntroduce;
        if (myIntroduceOptional.isEmpty()){
            myIntroduce = new MyIntroduce();
            myIntroduce.setMember(member);
            myIntroduce.setTitle("Greetings!");
            myIntroduce.setContents("I'm " + member.getName() + " and I want to travel With you!");
        }
        else{
            myIntroduce = myIntroduceOptional.get();
        }
        MemberInfoDTO memberInfoDTO = MemberInfoDTO.entityToDto(member);
        return MyPageDTO.builder().memberInfoDTO(memberInfoDTO)
                .myIntroduceTitle(myIntroduce.getTitle())
                .myIntroduceContents(myIntroduce.getContents()).build();


    }
}
