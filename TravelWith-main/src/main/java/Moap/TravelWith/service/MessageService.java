package Moap.TravelWith.service;

import Moap.TravelWith.dto.MessageDTO;
import Moap.TravelWith.dto.MessageDetailDTO;
import Moap.TravelWith.dto.MessageListDTO;
import Moap.TravelWith.entity.Member;
import Moap.TravelWith.entity.Message;
import Moap.TravelWith.repository.MemberRepository;
import Moap.TravelWith.repository.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    public void sendMessage(String senderEmail, MessageDTO messageDTO) {
        // 발신자 정보 확인
        Member sender = memberRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new EntityNotFoundException("발신자를 찾을 수 없습니다."));

        // 수신자 정보 확인
        Member receiver = memberRepository.findByEmail(messageDTO.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("수신자를 찾을 수 없습니다."));

        // 새로운 메시지
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setTitle(messageDTO.getTitle());
        message.setContents(messageDTO.getContents());
        message.setSendTime(LocalDateTime.now()); // 메시지 보낸 시간 설정

        // 메시지 저장
        messageRepository.save(message);
    }

    public List<MessageListDTO> getSentMessages(String email) {
        Member sender = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("발신자 정보를 찾을 수 없습니다."));

        return messageRepository.findBySender(sender).stream().map(message ->
                        new MessageListDTO(message.getId(), message.getReceiver().getEmail(),
                                message.getTitle(), message.getSendTime()))
                .collect(Collectors.toList());
    }

    public List<MessageListDTO> getReceivedMessages(String email) {
        Member receiver = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("수신자 정보를 찾을 수 없습니다."));

        return messageRepository.findByReceiver(receiver).stream().map(message ->
                        new MessageListDTO(message.getId(), message.getSender().getEmail(),
                                message.getTitle(), message.getSendTime()))
                .collect(Collectors.toList());
    }

    public MessageDetailDTO getMessageDetails(Long messageId, String email) throws AccessDeniedException {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("쪽지를 찾을 수 없습니다."));

        // 이메일을 기반으로 발신자인지 수신자인지 확인
        String counterparty;
        if (message.getSender().getEmail().equals(email)) {
            // 발신자인 경우, 수신자 정보
            counterparty = message.getReceiver().getEmail();
        } else if (message.getReceiver().getEmail().equals(email)) {
            // 수신자인 경우, 발신자 정보
            counterparty = message.getSender().getEmail();
        } else {
            throw new CustomAccessDeniedException("접근 권한이 없습니다.");
        }
        return new MessageDetailDTO(counterparty, message.getTitle(),
                message.getContents(), message.getSendTime());
    }

    public class CustomAccessDeniedException extends RuntimeException {
        public CustomAccessDeniedException(String message) {
            super(message);
        }
    }

}
