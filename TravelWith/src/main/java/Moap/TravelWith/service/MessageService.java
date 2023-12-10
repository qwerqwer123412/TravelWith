package Moap.TravelWith.service;

import Moap.TravelWith.dto.messageDTO.write.MessageDetailDTO;
import Moap.TravelWith.dto.MessageListDTO;
import Moap.TravelWith.dto.match_response.MemberInfoDTO;
import Moap.TravelWith.dto.messageDTO.MessagePreview;
import Moap.TravelWith.dto.messageDTO.write.MessageWriteDTO;
import Moap.TravelWith.entity.Member;
import Moap.TravelWith.entity.Message;
import Moap.TravelWith.enumer.Me;
import Moap.TravelWith.repository.MemberRepository;
import Moap.TravelWith.repository.MessageQueryRepository;
import Moap.TravelWith.repository.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final MessageQueryRepository messageQueryRepository;
    public List<MessagePreview> findMessagePreviewList(String email){
        Member member = memberRepository.findByEmail(email).get();
        List<Member> messageParticipants = messageQueryRepository.findMessageParticipants(member);
        List<MemberInfoDTO> list = messageParticipants.stream().map(MemberInfoDTO::entityToDto).toList();
        return list.stream().map(memberInfoDTO -> {
            Member other  = memberRepository.findById(memberInfoDTO.getId()).get();
            Message recentMessage = messageQueryRepository.findRecentMessage(member, other);
            return MessagePreview.builder().memberInfoDTO(memberInfoDTO)
                    .contents(recentMessage.getContents())
                    .title(recentMessage.getTitle())
                    .rencentDate(recentMessage.getSendTime()).build();
        }).collect(Collectors.toSet()).stream().toList();
    }






    public void sendMessage(String senderEmail, MessageWriteDTO messageDTO) {
        // 발신자 정보 확인
        Member sender = memberRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new EntityNotFoundException("발신자를 찾을 수 없습니다."));

        // 수신자 정보 확인
        Member receiver = memberRepository.findByEmail(messageDTO.getReceiverEmail())
                .orElseThrow(() -> new EntityNotFoundException("수신자를 찾을 수 없습니다."));
        log.info("2");
        // 새로운 메시지
        Message message = Message.builder().sender(sender)
                .receiver(receiver)
                .sendTime(LocalDateTime.now())
                .contents(messageDTO.getContents())
                .title(messageDTO.getTitle()).build();
        log.info("3");
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

    public List<MessageDetailDTO> getMessageWithOthersEmail(String othersEmail, String email) throws AccessDeniedException {
        Member other = memberRepository.findByEmail(othersEmail).orElseThrow();
        Member member = memberRepository.findByEmail(email).get();
        List<Message> myMessageWithOthers = messageQueryRepository.findMyMessageWithOthers(member, other);
        List<MessageDetailDTO> list = myMessageWithOthers.stream().map(MessageDetailDTO::entityToDTO).toList();
        list.forEach(MessageDetailDTO -> {
            if (MessageDetailDTO.getSenderEmail().equals(email)){
                MessageDetailDTO.setMe(Me.MY_SEND);
            }
            else{
                MessageDetailDTO.setMe(Me.OTHERS_SEND);
            }
        });
        // 이메일을 기반으로 발신자인지 수신자인지 확인
        return list;
      /*  if (message.getSender().getEmail().equals(email)) {
            // 발신자인 경우, 수신자 정보
            counterparty = message.getReceiver().getEmail();
        } else if (message.getReceiver().getEmail().equals(email)) {
            // 수신자인 경우, 발신자 정보
            counterparty = message.getSender().getEmail();
        } else {
            throw new CustomAccessDeniedException("접근 권한이 없습니다.");
        }
        return new MessageDetailDTO(counterparty, message.getTitle(),
                message.getContents(), message.getSendTime());*/
    }



    public class CustomAccessDeniedException extends RuntimeException {
        public CustomAccessDeniedException(String message) {
            super(message);
        }
    }

}
