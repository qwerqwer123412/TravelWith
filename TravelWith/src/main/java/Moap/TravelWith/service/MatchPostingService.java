package Moap.TravelWith.service;


import Moap.TravelWith.dto.MatchPostingWrite;
import Moap.TravelWith.entity.MatchPosting;
import Moap.TravelWith.entity.MatchStatus;
import Moap.TravelWith.entity.Member;
import Moap.TravelWith.enumer.MatchRole;
import Moap.TravelWith.repository.MatchPostingRepository;
import Moap.TravelWith.repository.MemberRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional(readOnly = true)
@Slf4j
public class MatchPostingService {


    private final MatchPostingRepository matchPostingRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void writeMatchPosting(MatchPostingWrite matchPostingWrite, Member writer){
        MatchPosting matchPosting = MatchPostingWrite.entityToDTO(matchPostingWrite);
        matchPostingRepository.joinMatchPosting(matchPosting);
        MatchStatus matchStatus = MatchStatus.makeEntity(writer, matchPosting, MatchRole.HOST);
        matchPostingRepository.joinMatchStatus(matchStatus);

    }
    @Transactional
    public void writeMatchPosting(MatchPostingWrite matchPostingWrite, Long memberId){
        Member writer = memberRepository.findMemberById(memberId);
        MatchPosting matchPosting = MatchPostingWrite.entityToDTO(matchPostingWrite);
        matchPostingRepository.joinMatchPosting(matchPosting);
        MatchStatus matchStatus = MatchStatus.makeEntity(writer, matchPosting, MatchRole.HOST);
        matchPostingRepository.joinMatchStatus(matchStatus);

    }

    //여기까지 posting글 작성
    @Transactional
    public void joinMatch(Long memberId, Long matchPostingId){
        Member member = memberRepository.findMemberById(memberId);
        MatchPosting matchPosting = matchPostingRepository.findMatchPostingById(matchPostingId);
        log.info("--------------------------");
        log.info(matchPosting.toString());
        log.info(matchPostingRepository.findMatchPeoplesNumber(matchPosting).toString());
        if( matchPostingRepository.findMatchPeoplesNumber(matchPosting)>= matchPosting.getNumOfPeoples()){
            throw new RuntimeException("자리가 꽉 찼습니다");
        }
        MatchStatus matchStatus = MatchStatus.makeEntity(member, matchPosting, MatchRole.PARTICIPANT);

        matchPostingRepository.joinMatchStatus(matchStatus);


    }








}
