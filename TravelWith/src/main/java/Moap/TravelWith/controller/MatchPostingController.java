package Moap.TravelWith.controller;


import Moap.TravelWith.dto.AssessmentSendsDTO;
import Moap.TravelWith.dto.MatchPostingWrite;
import Moap.TravelWith.dto.PostingSearchDto;
import Moap.TravelWith.dto.match_response.MatchResponse;
import Moap.TravelWith.dto.match_response.MatchResponseDetail;
import Moap.TravelWith.dto.match_response.MemberInfoDTO;
import Moap.TravelWith.entity.Member;
import Moap.TravelWith.exception.NoLoginMemberFoundException;
import Moap.TravelWith.repository.LoginCheckRepository;
import Moap.TravelWith.service.MatchPostingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/match-posting")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class MatchPostingController {

    private final MatchPostingService matchPostingService;
    private final LoginCheckRepository loginCheckRepository;


    //매칭글 작성
    @PostMapping("/write")
    public ResponseEntity<String> writing(@RequestBody MatchPostingWrite matchPostingWrite,
                                          @RequestHeader String email) {
        loginCheck(email);
        matchPostingService.writeMatchPosting(matchPostingWrite, email);

        return ResponseEntity.ok("글 작성 완료");

    }


    //매칭글에 참여
    @PostMapping("/join/{matchPostingId}")
    public ResponseEntity<String> joining(
            @PathVariable Long matchPostingId,
            @RequestHeader String email) {

        loginCheck(email);
        matchPostingService.joinMatch(email, matchPostingId);
        return ResponseEntity.ok("join완료");

    }


    //매칭글에 검색 (조건이 있는 ..)
    @GetMapping("/search-condition")
    public List<MatchResponseDetail> searchWithCondition(
            @RequestParam(name = "startDate", required = false) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) LocalDate endDate,
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "money", required = false) Integer money
            , @RequestHeader String email) {
        loginCheck(email);
        PostingSearchDto dto = PostingSearchDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .money(money)
                .query(query)
                .build();
        return matchPostingService.searchMatchPostingWithCondition(dto);
    }

    @GetMapping("/search/{query}")
    public List<MatchResponseDetail> search(@PathVariable(required = false) String query, @RequestHeader String email) {
        loginCheck(email);

        return matchPostingService.searchMatchPostingWithString(query);
    }
    @GetMapping("/search")
    public List<MatchResponseDetail> search_empty(@RequestHeader String email) {
        loginCheck(email);

        return matchPostingService.searchMatchPostingWithString("");
    }


    /*@GetMapping("/search-detail/{matchId}")
    public MatchResponseDetail getDetailInfo(@PathVariable Long matchId, @RequestHeader String email){
        loginCheck(email);
        return matchPostingService.getDetailInfo(matchId);

    }*/

    //나의 매칭 링스트

    //종료된것 부터
    @GetMapping("/ended-match")
    public List<MatchResponseDetail> endedMatch(@RequestHeader String email) {
        loginCheck(email);
        return matchPostingService.findEndedMyMatchPosting(email);
    }


    @PostMapping("/ended-match/assessment")
    public String assessment(@RequestBody
                             AssessmentSendsDTO dto, @RequestHeader String email) {

        loginCheck(email);
        log.info(String.valueOf(dto));
        matchPostingService.assessMember(dto.getMemberId(), dto.getAssessedMemberId(), dto.getMatchId()
                , dto.getPoints());
        return "평가 성공";
    }

    //현재 진행형인 매칭리스트들
    @GetMapping("/progress-match")
    public List<MatchResponseDetail> progressMatch(@RequestHeader String email) {

        loginCheck(email);
        return matchPostingService.findProgressMyMatchPosting(email);
    }


    private void loginCheck(String email) {
        if (loginCheckRepository.findLoginCheckByEmail(email).isEmpty()) {
            throw new NoLoginMemberFoundException("로그인 한 회원이 존재하지 않습니다");
        }
    }

}
