package Moap.TravelWith.controller;


import Moap.TravelWith.dto.MatchPostingWrite;
import Moap.TravelWith.dto.PostingSearchDto;
import Moap.TravelWith.entity.MatchPosting;
import Moap.TravelWith.service.MatchPostingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/matchPosting")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class MatchPostingController {

    private final MatchPostingService matchPostingService;

    @PostMapping("/write/{memberId}")
    public ResponseEntity<String> writing(@RequestBody MatchPostingWrite matchPostingWrite,
                                          @PathVariable Long memberId) {

        matchPostingService.writeMatchPosting(matchPostingWrite, memberId);
        log.info(matchPostingWrite.toString());

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


}
