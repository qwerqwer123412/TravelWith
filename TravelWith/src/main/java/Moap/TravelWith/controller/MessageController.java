package Moap.TravelWith.controller;

import Moap.TravelWith.dto.messageDTO.write.MessageDetailDTO;
import Moap.TravelWith.dto.messageDTO.MessagePreview;
import Moap.TravelWith.dto.messageDTO.write.MessageWriteDTO;
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
                                         @RequestBody MessageWriteDTO meesageWriteDTO) {
        loginCheck(email);
        log.info("1");
        messageService.sendMessage(email, meesageWriteDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<MessagePreview>> getMessagePreview(@RequestHeader String email){
        loginCheck(email);

        List<MessagePreview> messagePreviewList = messageService.findMessagePreviewList(email);
        return ResponseEntity.ok(messagePreviewList);
    }
    @GetMapping("/{othersEmail}")
    public ResponseEntity<List<MessageDetailDTO>> getMessageDetails(@PathVariable String othersEmail,
                                                              @RequestHeader String email) throws AccessDeniedException {
        loginCheck(email);
        List<MessageDetailDTO> messageWithOthersEmail = messageService.getMessageWithOthersEmail(othersEmail, email);
        return ResponseEntity.ok(messageWithOthersEmail);
    }



    private void loginCheck(String email) {
        if (loginCheckRepository.findLoginCheckByEmail(email).isEmpty()) {
            throw new NoLoginMemberFoundException("로그인 한 회원이 존재하지 않습니다");
        }
    }
}
