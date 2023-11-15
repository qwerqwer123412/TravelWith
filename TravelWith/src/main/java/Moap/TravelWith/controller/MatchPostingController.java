package Moap.TravelWith.controller;


import Moap.TravelWith.dto.AssessmentSendsDTO;
import Moap.TravelWith.dto.MatchPostingWrite;
import Moap.TravelWith.dto.PostingSearchDto;
import Moap.TravelWith.entity.MatchPosting;
import Moap.TravelWith.entity.Member;
import Moap.TravelWith.service.MatchPostingService;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/write/{memberId}")
    public ResponseEntity<String> writing(@RequestBody MatchPostingWrite matchPostingWrite,
                                          @PathVariable Long memberId) {

        matchPostingService.writeMatchPosting(matchPostingWrite, memberId);

        return ResponseEntity.ok("글 작성 완료");

    }

    @PostMapping("/join/{memberId}/{matchPostingId}")
    public ResponseEntity<String> joining(@PathVariable Long memberId,
                                          @PathVariable Long matchPostingId) {
        log.info("hi");
        matchPostingService.joinMatch(memberId, matchPostingId);
        return ResponseEntity.ok("join완료");

    }

    @GetMapping("/search")
    public List<MatchPosting> search(
            @RequestParam(name = "startDate", required = false) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) LocalDate endDate,
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "money", required = false) Integer money) {

        PostingSearchDto dto = PostingSearchDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .money(money)
                .query(query)
                .build();
        return matchPostingService.searchMatchPosting(dto);
    }


    @GetMapping("/ended-match/{memberId}")
    public List<MatchPosting> endedMatch(@PathVariable Long memberId){

        return matchPostingService.findEndedMyMatchPosting(memberId);
    }

    @GetMapping("/ended-match/{memberId}/{matchId}")
    public List<Member> endedMatchMembers(
            @PathVariable Long memberId,
            @PathVariable Long matchId){
        return matchPostingService.findMatchPostingMembers(matchId);
    }

    @PostMapping("/ended-match/assessment/{memberId}/{matchId}/{assessedMemberId}/{points}")
    public String assessment(@RequestBody
                             AssessmentSendsDTO dto){
        matchPostingService.assessMember(dto.getMemberId(), dto.getAssessedMemberId(), dto.getMatchId()
                ,dto.getPoints());
        return "평가 성공";
    }

}
