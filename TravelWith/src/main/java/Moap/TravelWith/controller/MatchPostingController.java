package Moap.TravelWith.controller;


import Moap.TravelWith.dto.MatchPostingWrite;
import Moap.TravelWith.service.MatchPostingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
