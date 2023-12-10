package Moap.TravelWith.repository;

import Moap.TravelWith.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
@Slf4j
public class MessageQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory jqf;
    private final QMatchPosting qMatchPosting = new QMatchPosting("qm");
    private final QMatchStatus qMatchStatus = new QMatchStatus("qMatchStatus");
    private final QMember qMember = new QMember("member");
    private final QMessage qMessage = new QMessage("message");

    //나와 메시지 주고받은 사람들의 리스트
    public List<Member> findMessageParticipants(Member member) {

        List<Message> myMessages = jqf
                .select(qMessage)
                .from(qMessage).where(
                        qMessage.sender.eq(member).or(qMessage.receiver.eq(member))).fetch();
        List<Member> msg1 = myMessages.stream().map(Message::getSender).toList();
        List<Member> msg2 = myMessages.stream().map(Message::getReceiver).toList();
        List<Member> result = new ArrayList<>();
        msg1.forEach(member1 -> {
            if (!member1.getId().equals(member.getId())){
                result.add(member1);
            }
        });
        msg2.forEach(member1 -> {
            if (!member1.getId().equals(member.getId())){
                result.add(member1);
            }
        });
        return result;
    }

    public Message findRecentMessage(Member member1, Member member2){
        return jqf.selectFrom(qMessage)
                .where((qMessage.sender.eq(member1).and(qMessage.receiver.eq(member2)))
                        .or(qMessage.sender.eq(member2).and(qMessage.receiver.eq(member1))))
                .orderBy(qMessage.sendTime.desc()) // sendTime을 기준으로 내림차순 정렬
                .fetchFirst(); // 결과 리스트의 첫 번째 요소 가져오기
    }


    public List<Message> findMyMessageWithOthers(Member member, Member other){
        return jqf.selectFrom(qMessage)
                .where((qMessage.sender.eq(member).and(qMessage.receiver.eq(other)))
                        .or((qMessage.receiver.eq(member).and(qMessage.sender.eq(other)))))
                .orderBy(qMessage.sendTime.desc()) // sendTime을 기준으로 내림차순 정렬
                .fetch(); // 결과 리스트의 첫 번째 요소 가져오기
    }


}
