package Moap.TravelWith.service;


import Moap.TravelWith.dto.MatchPostingWrite;
import Moap.TravelWith.dto.PostingSearchDto;
import Moap.TravelWith.dto.match_response.MatchResponse;
import Moap.TravelWith.dto.match_response.MatchResponseDetail;
import Moap.TravelWith.dto.match_response.MemberInfoDTO;
import Moap.TravelWith.entity.Assessment;
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

    //매칭글 작성
    @Transactional
    public void writeMatchPosting(MatchPostingWrite matchPostingWrite, String memberEmail) {

        Optional<Member> byEmail = memberRepository.findByEmail(memberEmail);
        if (byEmail.isEmpty()) {
            throw new RuntimeException("No member Exception");
        }
        Member writer = byEmail.get();
        MatchPosting matchPosting = MatchPostingWrite.entityToDTO(matchPostingWrite);
        if (matchPosting == null) {
            throw new RuntimeException("No matchPosting Exception");
        }
        matchPostingRepository.joinMatchPosting(matchPosting);
        MatchStatus matchStatus = MatchStatus.makeEntity(writer, matchPosting, MatchRole.HOST);
        matchPostingRepository.joinMatchStatus(matchStatus);

    }

    //매칭글 참여
    @Transactional
    public void joinMatch(String email, Long matchPostingId) {
        Optional<Member> byEmail = memberRepository.findByEmail(email);

        if (!byEmail.isPresent()) {
            throw new RuntimeException("No member Exception");
        }
        Member member = byEmail.get();
        MatchPosting matchPosting = matchPostingRepository.findMatchPostingById(matchPostingId);
        if (matchPosting == null) {
            throw new RuntimeException("No matchPosting Exception");
        }
        if (matchPostingRepository.findMatchPeoplesNumber(matchPosting) >= matchPosting.getNumOfPeoples()) {
            throw new RuntimeException("자리가 꽉 찼습니다");
        }
        MatchStatus matchStatus = MatchStatus.makeEntity(member, matchPosting, MatchRole.PARTICIPANT);

        matchPostingRepository.joinMatchStatus(matchStatus);


    }


    //매칭글 검색
    public List<MatchResponseDetail> searchMatchPostingWithCondition(PostingSearchDto postingSearchDto) {
        postingSearchDto.setStartDate(Optional.ofNullable(postingSearchDto.getStartDate()).orElse(LocalDate.of(1900, 1, 1)));
        postingSearchDto.setEndDate(Optional.ofNullable(postingSearchDto.getEndDate()).orElse(LocalDate.of(2200, 1, 1)));
        postingSearchDto.setMoney(Optional.ofNullable(postingSearchDto.getMoney()).orElse(Integer.MAX_VALUE));
        postingSearchDto.setQuery(Optional.ofNullable(postingSearchDto.getQuery()).orElse(""));
        System.out.println(postingSearchDto);
        List<MatchPosting> matchPostings = matchPostingRepository.findMatchPostingDetail(postingSearchDto);

        matchPostings.forEach(System.out::println);
        return matchPostings.stream().map(matchPosting -> this.getDetailInfo(matchPosting.getId())).toList();



    }

    public List<MatchResponseDetail> searchMatchPostingWithString(String query) {
        List<MatchPosting> matchPostings = matchPostingRepository.findMatchPostingWithString(query);
        return matchPostings.stream().map(matchPosting -> this.getDetailInfo(matchPosting.getId())).toList();

    }

    private List<MatchResponse> sethost(List<MatchPosting> matchPostings) {
        List<MatchResponse> list = matchPostings.stream().map(MatchResponse::entityToDTO).toList();
        list.forEach(matchResponse -> {
            MatchPosting matchPosting = matchPostingRepository.findMatchPostingById(matchResponse.getMatchId());
            Member host = matchPostingRepository.findHost(matchPosting).get(0);
            matchResponse.setHostName(host.getName());
        });
        return list;
    }

    //매칭글 detail정보 찾기


    //종료된 매칭글 찾아주기
    public List<MatchResponseDetail> findEndedMyMatchPosting(String memberEmail) {
        Optional<Member> byEmail = memberRepository.findByEmail(memberEmail);
        if (byEmail.isEmpty()) {
            throw new RuntimeException("No memberFind Exception");
        }
        Member member = byEmail.get();
        List<MatchPosting> matchPostings = matchPostingRepository.findAllEndedMatchPosting(member.getId());

        return matchPostings.stream().map(matchPosting -> this.getDetailInfo(matchPosting.getId())).toList();

    }

    public List<MatchResponseDetail>findProgressMyMatchPosting(String memberEmail) {
        Optional<Member> byEmail = memberRepository.findByEmail(memberEmail);
        if (byEmail.isEmpty()) {
            throw new RuntimeException("No memberFind Exception");
        }
        Member member = byEmail.get();
        List<MatchPosting> matchPostings = matchPostingRepository.findAllMatchPostingProgress(member.getId());

        List<MatchResponseDetail> list = matchPostings.stream().map(matchPosting -> this.getDetailInfo(matchPosting.getId())).toList();
        return list.stream().filter(matchResponseDetail -> matchResponseDetail.getEndDate().isAfter(LocalDate.now())).toList();

    }

    public List<Member> findMatchPostingMembers(Long matchId) {
        MatchPosting matchPosting = matchPostingRepository.findMatchPostingById(matchId);
        return matchPostingRepository.findMatchingJoiner(matchPosting);
    }

    @Transactional
    public void assessMember(Long loginMemberId, Long assessedMemberId, Long matchPostingId, Integer points) {
        Member loginMember = memberRepository.findMemberById(loginMemberId);
        Member assessedMember = memberRepository.findMemberById(assessedMemberId);
        MatchPosting matchPosting = matchPostingRepository.findMatchPostingById(matchPostingId);
        if (loginMember == null || assessedMember == null) {
            throw new RuntimeException("No memberException");
        }
        if (matchPosting == null) {
            throw new RuntimeException("No matchPostingException");
        }
        List<Assessment> asessment = matchPostingRepository.findAsessment(loginMember, assessedMember, matchPosting);
        if (asessment.isEmpty()) {
            Assessment build = Assessment.builder().evaluator(loginMember).receiver(assessedMember).matchPosting(matchPosting).points(points).build();
            matchPostingRepository.joinAsessment(build);
        } else {
            Assessment assessment = asessment.get(0);
            assessment.setPoints(points);
        }


    }






    public MatchResponseDetail getDetailInfo(Long matchId) {
        MatchPosting matchPosting = matchPostingRepository.findMatchPostingById(matchId);
        if (matchPosting == null) {
            throw new RuntimeException("존재하지 않는 matching글입니다");
        }
        List<Member> hosts = matchPostingRepository.findHost(matchPosting);
        if (hosts.isEmpty()) {
            throw new RuntimeException("BAD REQUEST");
        }
        Member host = hosts.get(0);
        List<Member> participant = matchPostingRepository.findParticipant(matchPosting);
        List<MemberInfoDTO> participants = participant.stream().map(MemberInfoDTO::entityToDto).toList();
        return MatchResponseDetail.builder().startDate(matchPosting.getStartDate())
                .matchId(matchPosting.getId())
                .endDate(matchPosting.getEndDate())
                .title(matchPosting.getTitle())
                .contents(matchPosting.getContents())
                .travelExpenses(matchPosting.getTravelExpenses())
                .numOfPeoples(matchPosting.getNumOfPeoples())
                .isAccommodationTogether(matchPosting.getIsAccommodationTogether())
                .isDiningTogether(matchPosting.getIsDiningTogether())
                .mainTravelSpace(matchPosting.getMainTravelSpace())
                .host(MemberInfoDTO.entityToDto(host)).participants(participants).build();

    }

}
