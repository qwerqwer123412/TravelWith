package Moap.TravelWith.service;

import Moap.TravelWith.entity.Member;
import Moap.TravelWith.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void test(Member member){
        memberRepository.save(member);
    }

}
