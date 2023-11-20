package Moap.TravelWith.controller;


import Moap.TravelWith.dto.AssessmentSendsDTO;
import Moap.TravelWith.dto.MatchPostingWrite;
import Moap.TravelWith.dto.PostingSearchDto;
import Moap.TravelWith.entity.MatchPosting;
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

    @PostMapping("/write")
    public ResponseEntity<String> writing(@RequestBody MatchPostingWrite matchPostingWrite,
                                          @RequestHeader String email

    ) {
        loginCheck(email);
        matchPostingService.writeMatchPosting(matchPostingWrite, email);

        return ResponseEntity.ok("글 작성 완료");

    }

    @PostMapping("/join/{matchPostingId}")
    public ResponseEntity<String> joining(
                                          @PathVariable Long matchPostingId,
                                          @RequestHeader String email) {

        loginCheck(email);
        matchPostingService.joinMatch(email, matchPostingId);
        return ResponseEntity.ok("join완료");

    }

    @GetMapping("/search")
    public List<MatchPosting> search(
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
        return matchPostingService.searchMatchPosting(dto);
    }


    @GetMapping("/ended-match")
    public List<MatchPosting> endedMatch(@RequestHeader String email) {
        loginCheck(email);
        return matchPostingService.findEndedMyMatchPosting(email);
    }

    @GetMapping("/ended-match/{matchId}")
    public List<Member> endedMatchMembers(
            @PathVariable Long matchId,
            @RequestHeader String email) {
        loginCheck(email);
        return matchPostingService.findMatchPostingMembers(matchId);
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


    private void loginCheck(String email) {
        if (loginCheckRepository.findLoginCheckByEmail(email).isEmpty()) {
            throw new NoLoginMemberFoundException("로그인 한 회원이 존재하지 않습니다");
        }
    }

}
