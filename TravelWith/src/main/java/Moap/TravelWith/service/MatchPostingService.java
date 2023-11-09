package Moap.TravelWith.service;


import Moap.TravelWith.dto.MatchPostingWrite;
import Moap.TravelWith.dto.PostingSearchDto;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


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
        if (writer == null){
            throw new RuntimeException("No member Exception");
        }
        MatchPosting matchPosting = MatchPostingWrite.entityToDTO(matchPostingWrite);
        if (matchPosting == null){
            throw new RuntimeException("No matchPosting Exception");
        }
        matchPostingRepository.joinMatchPosting(matchPosting);
        MatchStatus matchStatus = MatchStatus.makeEntity(writer, matchPosting, MatchRole.HOST);
        matchPostingRepository.joinMatchStatus(matchStatus);

    }

    //여기까지 posting글 작성
    @Transactional
    public void joinMatch(Long memberId, Long matchPostingId){
        Member member = memberRepository.findMemberById(memberId);
        if (member == null){
            throw new RuntimeException("No member Exception");
        }
        MatchPosting matchPosting = matchPostingRepository.findMatchPostingById(matchPostingId);
        if (matchPosting == null){
            throw new RuntimeException("No matchPosting Exception");
        }
        if( matchPostingRepository.findMatchPeoplesNumber(matchPosting)>= matchPosting.getNumOfPeoples()){
            throw new RuntimeException("자리가 꽉 찼습니다");
        }
        MatchStatus matchStatus = MatchStatus.makeEntity(member, matchPosting, MatchRole.PARTICIPANT);

        matchPostingRepository.joinMatchStatus(matchStatus);


    }

    public List<MatchPosting> searchMatchPosting(PostingSearchDto postingSearchDto){
        postingSearchDto.setStartDate(Optional.ofNullable(postingSearchDto.getStartDate()).orElse(LocalDate.of(1900, 1, 1)));
        postingSearchDto.setEndDate(Optional.ofNullable(postingSearchDto.getEndDate()).orElse(LocalDate.of(2200, 1, 1)));
        postingSearchDto.setMoney(Optional.ofNullable(postingSearchDto.getMoney()).orElse(Integer.MAX_VALUE));
        postingSearchDto.setQuery(Optional.ofNullable(postingSearchDto.getQuery()).orElse(""));
        return matchPostingRepository.findMatchPosting(postingSearchDto);


    }








}
