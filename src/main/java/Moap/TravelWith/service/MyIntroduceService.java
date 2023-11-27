package Moap.TravelWith.service;

import Moap.TravelWith.dto.MyIntroduceDTO;
import Moap.TravelWith.entity.Member;
import Moap.TravelWith.entity.MyIntroduce;
import Moap.TravelWith.repository.MemberRepository;
import Moap.TravelWith.repository.MyIntroduceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyIntroduceService {

    private final MyIntroduceRepository myIntroduceRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public MyIntroduceService(MyIntroduceRepository myIntroduceRepository, MemberRepository memberRepository) {
        this.myIntroduceRepository = myIntroduceRepository;
        this.memberRepository = memberRepository;
    }

    public MyIntroduce createMyIntroduce(MyIntroduceDTO introduceDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        MyIntroduce myIntroduce = new MyIntroduce();
        myIntroduce.setMember(member);
        myIntroduce.setTitle(introduceDTO.getTitle());
        myIntroduce.setContents(introduceDTO.getContents());

        return myIntroduceRepository.save(myIntroduce);
    }
}
