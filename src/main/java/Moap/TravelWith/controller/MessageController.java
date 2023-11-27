package Moap.TravelWith.controller;

import Moap.TravelWith.dto.MessageDTO;
import Moap.TravelWith.dto.MessageDetailDTO;
import Moap.TravelWith.dto.MessageListDTO;
import Moap.TravelWith.exception.NoLoginMemberFoundException;
import Moap.TravelWith.repository.LoginCheckRepository;
import Moap.TravelWith.service.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class MessageController {

    private final MessageService messageService;
    private final LoginCheckRepository loginCheckRepository;

    @PostMapping("/write")
    public ResponseEntity<?> sendMessage(@RequestHeader String email,
                                         @RequestBody MessageDTO messageDTO) {
        loginCheck(email);
        messageService.sendMessage(email, messageDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/send")
    public ResponseEntity<List<MessageListDTO>> getSentMessages(@RequestHeader String email) {
        loginCheck(email);
        return ResponseEntity.ok(messageService.getSentMessages(email));
    }

    @GetMapping("/receive")
    public ResponseEntity<List<MessageListDTO>> getReceivedMessages(@RequestHeader String email) {
        loginCheck(email);
        return ResponseEntity.ok(messageService.getReceivedMessages(email));
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<MessageDetailDTO> getMessageDetails(@PathVariable Long messageId,
                                                              @RequestHeader String email) throws AccessDeniedException {
        loginCheck(email);
        MessageDetailDTO messageDetails = messageService.getMessageDetails(messageId, email);
        return ResponseEntity.ok(messageDetails);
    }

    private void loginCheck(String email) {
        if (loginCheckRepository.findLoginCheckByEmail(email).isEmpty()) {
            throw new NoLoginMemberFoundException("로그인 한 회원이 존재하지 않습니다");
        }
    }
}
